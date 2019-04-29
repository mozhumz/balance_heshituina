package com.mozhumz.balance.service.impl;

import com.mozhumz.balance.model.entity.CustomerBalance;
import com.mozhumz.balance.mapper.ICustomerBalanceMapper;
import com.mozhumz.balance.service.ICustomerBalanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lshaci
 * @since 2019-04-29
 */
@Service
public class CustomerBalanceServiceImpl extends ServiceImpl<ICustomerBalanceMapper, CustomerBalance> implements ICustomerBalanceService {

}
