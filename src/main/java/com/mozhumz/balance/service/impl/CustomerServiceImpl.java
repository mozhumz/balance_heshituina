package com.mozhumz.balance.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyj.util.exception.BaseException;
import com.hyj.util.param.CheckParamsUtil;
import com.hyj.util.web.JsonResponse;
import com.mozhumz.balance.enums.ErrorCode;
import com.mozhumz.balance.mapper.IUserMapper;
import com.mozhumz.balance.model.dto.BalanceDto;
import com.mozhumz.balance.model.entity.Customer;
import com.mozhumz.balance.mapper.ICustomerMapper;
import com.mozhumz.balance.model.entity.Product;
import com.mozhumz.balance.model.entity.User;
import com.mozhumz.balance.model.qo.BalanceLogQo;
import com.mozhumz.balance.model.qo.CustomerQo;
import com.mozhumz.balance.service.ICustomerBalanceLogService;
import com.mozhumz.balance.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mozhumz.balance.utils.MD5Util;
import com.mozhumz.balance.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    @Resource
    private IUserMapper userMapper;
    @Resource
    private ICustomerBalanceLogService customerBalanceLogService;

    /**
     * 导入Excel-添加客户
     *
     * @param file
     * @return
     */
    @Override
    @Transactional
    public JsonResponse addCustomer(MultipartFile file) {
        int okNum = 0;
        int noNum = 0;
        try {
            ImportParams params = new ImportParams();
//            params.setTitleRows(1);
            params.setHeadRows(1);
            List<Customer> list = ExcelImportUtil.importExcel(
                    file.getInputStream(),
                    Customer.class, params);
            if (CollectionUtils.isEmpty(list)) {
                return JsonResponse.success(null);
            }
            for (Customer customer : list) {
                if (saveCustomer(customer,1)) continue;
                okNum++;

            }
            noNum = list.size() - okNum;
        } catch (BaseException e) {

            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("addCustomer-err:" + e);
            throw new BaseException(ErrorCode.CUSTOMER_ADD_ERR.desc);
        }

        return JsonResponse.success("成功条数：" + okNum + ",失败条数：" + noNum);
    }

    /**
     * 添加客户
     *
     * @param customer
     * @param type  1 导入客户 null 操作人添加客户
     * @return
     */
    public boolean saveCustomer(Customer customer,Integer type) {
        CheckParamsUtil.checkObj(customer);
        //校验操作人
        if(type==null){
            customerBalanceLogService.checkDoUser(customer.getDoUserId(), customer.getDoPassword());
        }

        if (customer.getMoney() < 0) {
            throw new BaseException(ErrorCode.CUSTOMER_MONEY_ERR.desc);
        }
        if (CheckParamsUtil.check(customer.getPassword())) {
            customer.setPassword(MD5Util.md5(customer.getPassword(), MD5Util.BALANCE_KEY));
        } else {
            customer.setPassword(MD5Util.getDefaultBalancePwd());
        }
        customer.setCreateDate(new Date());
        customer.setUpdateDate(new Date());
        try {
            customer.insert();
        } catch (DuplicateKeyException e) {
            throw new BaseException(ErrorCode.CUSTOMER_PHONE_ERR.desc + ":" + customer.getPhone());
        }
        return false;
    }


    /**
     * 获取客户列表
     *
     * @param customerQo
     * @return
     */
    @Override
    public JsonResponse getCustomerList(CustomerQo customerQo) {
        Page page = new Page(customerQo.getPage(), customerQo.getSize());

        return JsonResponse.success(customerMapper.findCustomerList(page, customerQo));
    }

    /**
     * 获取客户详情
     *
     * @param customerId
     * @return
     */
    @Override
    public JsonResponse getCustomer(Long customerId) {
        if (customerId == null) {
            JsonResponse.success(null);
        }

        return JsonResponse.success(getById(customerId).setPassword(null));
    }

    /**
     * 更新客户信息
     *
     * @param customer
     * @return
     */
    @Override
    public JsonResponse updateCustomer(Customer customer) {
        if (customer.getId() == null) {
            throw new BaseException(ErrorCode.PARAM_ERR.desc);
        }
        CheckParamsUtil.checkObj(customer);
        if (CheckParamsUtil.check(customer.getPassword())) {
            //修改客户密码 校对验证码
            if (!CheckParamsUtil.check(customer.getEmailCode())
                    || !customer.getEmailCode().equals(SessionUtil.getEmailCode(customer.getEmail(), customer.getId()))) {
                throw new BaseException(ErrorCode.EMAIL_CODE_ERR.desc);
            }
            customer.setIs0pwd(2);


        }
        customer.setPassword(MD5Util.getBalancePwd(customer.getPassword()));
        customer.setUpdateDate(new Date());
        return JsonResponse.success(updateById(customer));
    }

}
