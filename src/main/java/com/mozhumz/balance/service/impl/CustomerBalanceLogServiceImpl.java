package com.mozhumz.balance.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyj.util.param.CheckParamsUtil;
import com.mozhumz.balance.enums.BalanceTypeEnum;
import com.mozhumz.balance.enums.ErrorCode;
import com.mozhumz.balance.mapper.IBalanceLogProductMapper;
import com.mozhumz.balance.mapper.ICustomerMapper;
import com.mozhumz.balance.model.dto.BalanceDto;
import com.mozhumz.balance.model.entity.BalanceLogProduct;
import com.mozhumz.balance.model.entity.Customer;
import com.mozhumz.balance.model.entity.CustomerBalanceLog;
import com.mozhumz.balance.mapper.ICustomerBalanceLogMapper;
import com.mozhumz.balance.model.qo.BalanceLogQo;
import com.mozhumz.balance.service.ICustomerBalanceLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mozhumz.balance.utils.CommonUtil;
import com.mozhumz.balance.utils.SessionUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lshaci.framework.common.exception.BaseException;
import top.lshaci.framework.web.model.JsonResponse;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 客户余额变动表（充值 消费） 服务实现类
 * </p>
 *
 * @author lshaci
 * @since 2019-05-27
 */
@Service
public class CustomerBalanceLogServiceImpl extends ServiceImpl<ICustomerBalanceLogMapper, CustomerBalanceLog>
        implements ICustomerBalanceLogService {
    @Resource
    private ICustomerBalanceLogMapper customerBalanceLogMapper;
    @Resource
    private ICustomerMapper customerMapper;
    @Resource
    private IBalanceLogProductMapper balanceLogProductMapper;


    /**
     * 客户充值/消费
     * @param balanceDto
     * @return
     */
    @Override
    @Transactional
    public JsonResponse addBalanceLog(BalanceDto balanceDto) {
        try {
            CheckParamsUtil.checkObj(balanceDto);
        }catch (Exception e){
            throw new BaseException(ErrorCode.PARAM_ERR.desc);
        }
        Customer customer=null;
        if(BalanceTypeEnum.consume.code==balanceDto.getType()){
            //消费
            if(!CheckParamsUtil.checkList_boolean(balanceDto.getProductIds())){
                throw new BaseException(ErrorCode.PARAM_ERR.desc);
            }
            //扣钱 t_customer
            customer = updateCustomer(balanceDto);
            //添加记录 t_customer_balance_log t_balance_log_product
            CustomerBalanceLog customerBalanceLog = addCustomerBalanceLog(balanceDto, customer);

            BalanceLogProduct balanceLogProduct=new BalanceLogProduct();
            balanceLogProduct.setBalanceLogId(customerBalanceLog.getId());
            balanceLogProduct.setCreateDate(new Date());
            balanceLogProduct.setUpdateDate(new Date());
            balanceLogProduct.setCustomerId(customer.getId());
            for(Long id:balanceDto.getProductIds()){
                balanceLogProduct.setProductId(id);
                balanceLogProduct.insert();
            }
        }else {
            //充值
            //加钱 t_customer
            customer = updateCustomer(balanceDto);
            //添加记录 t_customer_balance_log
            addCustomerBalanceLog(balanceDto, customer);
        }


        return JsonResponse.success(customer.setPassword(null));
    }

    /**
     * 添加余额变动记录
     * @param balanceDto
     * @param customer
     * @return
     */
    @Transactional
    private CustomerBalanceLog addCustomerBalanceLog(BalanceDto balanceDto, Customer customer) {
        CustomerBalanceLog customerBalanceLog=new CustomerBalanceLog();
        customerBalanceLog.setCustomerId(customer.getId());
        if(BalanceTypeEnum.consume.code==balanceDto.getType()){
            customerBalanceLog.setMoney(-balanceDto.getMoney());
        }else {
            customerBalanceLog.setMoney(balanceDto.getMoney());
        }
        customerBalanceLog.setType(balanceDto.getType());
        customerBalanceLog.setRemark(balanceDto.getRemark());
        customerBalanceLog.setProductIdStr(CommonUtil.getIdStr(balanceDto.getProductIds()));
        customerBalanceLog.setCreateDate(new Date());
        customerBalanceLog.setUpdateDate(new Date());
        customerBalanceLog.setDoName(balanceDto.getDoName());
        customerBalanceLog.setDoUserName(SessionUtil.getLoginUser().getUsername());
        customerBalanceLog.insert();

        return customerBalanceLog;
    }

    /**
     * 消费/充值 t_customer
     * @param balanceDto
     * @return
     */
    @Transactional
    private Customer updateCustomer(BalanceDto balanceDto) {
        Customer customer=customerMapper.selectById(balanceDto.getCustomerId());
        if(customer==null){
            throw new BaseException(ErrorCode.CUSTOMER_ERR.desc);
        }
        Double balance=0.0;
        if(BalanceTypeEnum.consume.code==balanceDto.getType()){
            //消费
            balance= CommonUtil.get45(customer.getMoney()-balanceDto.getMoney());
        }else {
            //充值
            balance= CommonUtil.get45(customer.getMoney()+balanceDto.getMoney());
        }

        if(balance<0){
            throw new BaseException(ErrorCode.CUSTOMER_BALANCE_ERR.desc);
        }
        customer.setMoney(balance);
        customer.setUpdateDate(new Date());
        customer.updateById();
        return customer;
    }

    /**
     * 获取余额变动列表（消费 充值）
     * @param balanceLogQo
     * @return
     */
    @Override
    public JsonResponse getBalanceLogList(BalanceLogQo balanceLogQo) {
        Page page=new Page(balanceLogQo.getPage(),balanceLogQo.getSize());

        return JsonResponse.success(customerBalanceLogMapper.findBalanceLogList(page,balanceLogQo));
    }
}
