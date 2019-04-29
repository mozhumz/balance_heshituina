package com.mozhumz.balance.service.impl;

import com.mozhumz.balance.model.entity.CustomerBalanceLog;
import com.mozhumz.balance.mapper.ICustomerBalanceLogMapper;
import com.mozhumz.balance.service.ICustomerBalanceLogService;
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
public class CustomerBalanceLogServiceImpl extends ServiceImpl<ICustomerBalanceLogMapper, CustomerBalanceLog> implements ICustomerBalanceLogService {

}
