package com.mozhumz.balance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyj.util.param.CheckParamsUtil;
import com.mozhumz.balance.enums.ErrorCode;
import com.mozhumz.balance.model.dto.BalanceDto;
import com.mozhumz.balance.model.entity.Customer;
import com.mozhumz.balance.mapper.ICustomerMapper;
import com.mozhumz.balance.model.entity.Product;
import com.mozhumz.balance.model.qo.BalanceLogQo;
import com.mozhumz.balance.model.qo.CustomerQo;
import com.mozhumz.balance.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mozhumz.balance.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import top.lshaci.framework.common.exception.BaseException;
import top.lshaci.framework.excel.handler.POIExcelUploadHandler;
import top.lshaci.framework.web.model.JsonResponse;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 客户信息表 服务实现类
 * </p>
 *
 * @author lshaci
 * @since 2019-05-27
 */
@Service
@Slf4j
public class CustomerServiceImpl extends ServiceImpl<ICustomerMapper, Customer> implements ICustomerService {
    @Resource
    private ICustomerMapper customerMapper;


    /**
     * 导入Excel-添加客户
     * @param file
     * @return
     */
    @Override
    @Transactional
    public JsonResponse addCustomer(MultipartFile file) {
        int okNum=0;
        int noNum=0;
        try {
            List<Customer> list= POIExcelUploadHandler.excel2Entities(file.getInputStream(),Customer.class);
            if(CollectionUtils.isEmpty(list)){
                return JsonResponse.success(null);
            }
            for(Customer customer:list){
                try {
                    CheckParamsUtil.checkObj(customer);
                    if(customer.getMoney()<0){
                        continue;
                    }
                    customer.setPassword(MD5Util.getDefaultBalancePwd());
                    save(customer);
                    okNum++;
                }catch (DuplicateKeyException e){
                    throw new BaseException(ErrorCode.CUSTOMER_PHONE_ERR.desc+":"+customer.getPhone());
                }catch (Exception e){
                    continue;
                }

            }
            noNum=list.size()-okNum;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("addCustomer-err:"+e);
            throw new BaseException(ErrorCode.CUSTOMER_ADD_ERR.desc);
        }

        return JsonResponse.success("成功条数："+okNum+",失败条数："+noNum);
    }


    /**
     * 获取客户列表
     * @param customerQo
     * @return
     */
    @Override
    public JsonResponse getCustomerList(CustomerQo customerQo) {
        Page page=new Page(customerQo.getPage(),customerQo.getSize());

        return JsonResponse.success(customerMapper.findCustomerList(page,customerQo));
    }

    /**
     * 获取客户详情
     * @param customerId
     * @return
     */
    @Override
    public JsonResponse getCustomer(Long customerId) {
        if(customerId==null){
            JsonResponse.success(null);
        }

        return JsonResponse.success(getById(customerId));
    }

    /**
     * 更新客户信息
     * @param customer
     * @return
     */
    @Override
    public JsonResponse updateCustomer(Customer customer) {
        if(customer.getId()==null){
            throw new BaseException(ErrorCode.PARAM_ERR.desc);
        }
        customer.setUpdateDate(new Date());
        return JsonResponse.success(updateById(customer));
    }

}
