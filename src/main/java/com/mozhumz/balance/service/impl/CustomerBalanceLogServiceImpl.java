package com.mozhumz.balance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyj.util.exception.BaseException;
import com.hyj.util.param.CheckParamsUtil;
import com.hyj.util.web.JsonResponse;
import com.mozhumz.balance.enums.BalanceTypeEnum;
import com.mozhumz.balance.enums.ErrorCode;
import com.mozhumz.balance.mapper.*;
import com.mozhumz.balance.model.dto.BalanceDto;
import com.mozhumz.balance.model.dto.ProductUserDto;
import com.mozhumz.balance.model.entity.*;
import com.mozhumz.balance.model.qo.BalanceLogQo;
import com.mozhumz.balance.service.ICustomerBalanceLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mozhumz.balance.utils.CommonUtil;
import com.mozhumz.balance.utils.MD5Util;
import com.mozhumz.balance.utils.SessionUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Resource
    private IUserMapper userMapper;
    @Resource
    private IRoleMapper roleMapper;


    /**
     * 客户充值/消费
     *
     * @param balanceDto
     * @return
     */
    @Override
    @Transactional
    public JsonResponse addBalanceLog(BalanceDto balanceDto) {
        //admin用户不能添加消费
        if ("admin".equals(SessionUtil.getLoginUser().getUsername())
                || "admin".equals(SessionUtil.getLoginUser().getRole().getName())) {
            throw new BaseException(ErrorCode.ADMIN_RIGHT_ERR.desc);
        }
        try {
            CheckParamsUtil.checkObj(balanceDto);
        } catch (Exception e) {
            throw new BaseException(ErrorCode.PARAM_ERR.desc);
        }
        Customer customer = null;
        if (BalanceTypeEnum.consume.code == balanceDto.getType()) {
            //消费
            if (!CheckParamsUtil.checkList_boolean(balanceDto.getProductUserList())) {
                throw new BaseException(ErrorCode.PARAM_ERR.desc);
            }
            //扣钱 t_customer
            customer = updateCustomer(balanceDto);
            //添加记录 t_customer_balance_log
            CustomerBalanceLog customerBalanceLog = addCustomerBalanceLog(balanceDto, customer);
            for (ProductUserDto productUserDto : balanceDto.getProductUserList()) {
                //t_balance_log_user
                BalanceLogUser balanceLogUser = addBalanceLogUser(customer, customerBalanceLog, productUserDto);
                for (Long productId : productUserDto.getProductIdList()) {
                    //t_balance_log_product
                    addBalanceLogProduct(customer, customerBalanceLog, productUserDto, balanceLogUser, productId);
                }
            }

        } else {
            //充值
            //加钱 t_customer
            customer = updateCustomer(balanceDto);
            //添加记录 t_customer_balance_log
            addCustomerBalanceLog(balanceDto, customer);
        }


        return JsonResponse.success(customer.setPassword(null));
    }

    private void addBalanceLogProduct(Customer customer, CustomerBalanceLog customerBalanceLog, ProductUserDto productUserDto, BalanceLogUser balanceLogUser, Long productId) {
        BalanceLogProduct balanceLogProduct = new BalanceLogProduct();
        balanceLogProduct.setBalanceLogId(customerBalanceLog.getId());
        balanceLogProduct.setBalanceLogUserId(balanceLogUser.getId());
        balanceLogProduct.setCustomerId(customer.getId());
        balanceLogProduct.setUserId(productUserDto.getUserId());
        balanceLogProduct.setProductId(productId);
        balanceLogProduct.setCreateDate(new Date());
        balanceLogProduct.setUpdateDate(new Date());
        balanceLogProduct.insert();
    }

    private BalanceLogUser addBalanceLogUser(Customer customer, CustomerBalanceLog customerBalanceLog, ProductUserDto productUserDto) {
        BalanceLogUser balanceLogUser = new BalanceLogUser();
        balanceLogUser.setBalanceLogId(customerBalanceLog.getId());
        balanceLogUser.setCustomerId(customer.getId());
        balanceLogUser.setUserId(productUserDto.getUserId());
        balanceLogUser.setCreateDate(new Date());
        balanceLogUser.setUpdateDate(new Date());
        balanceLogUser.insert();
        return balanceLogUser;
    }

    /**
     * 添加余额变动记录
     *
     * @param balanceDto
     * @param customer
     * @return
     */
    @Transactional
    private CustomerBalanceLog addCustomerBalanceLog(BalanceDto balanceDto, Customer customer) {
        CustomerBalanceLog customerBalanceLog = new CustomerBalanceLog();
        customerBalanceLog.setCustomerId(customer.getId());
        if (BalanceTypeEnum.consume.code == balanceDto.getType()) {
            customerBalanceLog.setMoney(-balanceDto.getMoney());
        } else {
            customerBalanceLog.setMoney(balanceDto.getMoney());
        }
        customerBalanceLog.setType(balanceDto.getType());
        customerBalanceLog.setRemark(balanceDto.getRemark());
        customerBalanceLog.setCreateDate(new Date());
        customerBalanceLog.setUpdateDate(new Date());
        customerBalanceLog.setDoUserId(balanceDto.getDoUserId());
        customerBalanceLog.setDoUserName(SessionUtil.getLoginUser().getUsername());
        customerBalanceLog.insert();

        return customerBalanceLog;
    }

    /**
     * 消费/充值 t_customer
     *
     * @param balanceDto
     * @return
     */
    @Transactional
    private Customer updateCustomer(BalanceDto balanceDto) {
        Customer customer = customerMapper.selectById(balanceDto.getCustomerId());
        if (customer == null) {
            throw new BaseException(ErrorCode.CUSTOMER_ERR.desc);
        }
        User doUser = userMapper.selectById(balanceDto.getDoUserId());
        if (doUser == null) {
            throw new BaseException(ErrorCode.USER_NOT_EXIST_ERR.desc);
        }
        //判断该员工是否有扣款权限
        Role role = getBalanceRole();
        if (!doUser.getRoleIdStr().contains("," + role.getId() + ",")) {
            throw new BaseException(ErrorCode.DOUSER_RIGHT_ERR.desc);
        }

        //操作员工密码校验
        if (MD5Util.getDefaultPwd().equals(doUser.getPassword())
                || MD5Util.getDefaultPwd().equals(doUser.getBalancePwd())) {
            throw new BaseException(ErrorCode.DOUSER_PWD0_ERR.desc);
        }
        if (!MD5Util.checkPwd(balanceDto.getEmpPassword(), doUser.getBalancePwd())) {
            throw new BaseException(ErrorCode.DOUSER_PWD_ERR.desc);
        }

        Double balance = 0.0;
        if (BalanceTypeEnum.consume.code == balanceDto.getType()) {
            //消费
            //客户密码校验
            if (!MD5Util.checkBalancePwd(balanceDto.getPassword(), customer.getPassword())) {
                throw new BaseException(ErrorCode.CUSTOMER_PWD_ERR.desc);
            }
            if (MD5Util.getDefaultBalancePwd().equals(MD5Util.getBalancePwd(balanceDto.getPassword()))) {
                throw new BaseException(ErrorCode.CUSTOMER_PWD0_ERR.desc);
            }
            balance = CommonUtil.get45(customer.getMoney() - balanceDto.getMoney());
        } else {
            //充值
            balance = CommonUtil.get45(customer.getMoney() + balanceDto.getMoney());
        }

        if (balance < 0) {
            throw new BaseException(ErrorCode.CUSTOMER_BALANCE_ERR.desc);
        }
        customer.setMoney(balance);
        customer.setUpdateDate(new Date());
        customer.updateById();
        return customer;
    }

    /**
     * 获取扣款角色
     *
     * @return
     */
    private Role getBalanceRole() {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "扣款");
        Role role = roleMapper.selectOne(queryWrapper);
        if (role == null) {
            throw new BaseException(ErrorCode.BALANCE_ROLE_NOT_EXIST_ERR.desc);
        }
        return role;
    }

    /**
     * 获取余额变动列表（消费 充值）
     *
     * @param balanceLogQo
     * @return
     */
    @Override
    public JsonResponse getBalanceLogList(BalanceLogQo balanceLogQo) {
        Page page = new Page(balanceLogQo.getPage(), balanceLogQo.getSize());

        return JsonResponse.success(customerBalanceLogMapper.findBalanceLogList(page, balanceLogQo));
    }
}
