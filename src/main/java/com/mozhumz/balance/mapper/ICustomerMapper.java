package com.mozhumz.balance.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mozhumz.balance.model.entity.Customer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mozhumz.balance.model.qo.CustomerQo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 客户信息表 Mapper 接口
 * </p>
 *
 * @author lshaci
 * @since 2019-05-27
 */
public interface ICustomerMapper extends BaseMapper<Customer> {
    IPage<Customer> findCustomerList(Page page, @Param("qo")CustomerQo qo);
}
