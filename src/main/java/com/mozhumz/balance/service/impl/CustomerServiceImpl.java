package com.mozhumz.balance.service.impl;

import com.mozhumz.balance.model.entity.Customer;
import com.mozhumz.balance.mapper.ICustomerMapper;
import com.mozhumz.balance.service.ICustomerService;
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
public class CustomerServiceImpl extends ServiceImpl<ICustomerMapper, Customer> implements ICustomerService {

}
