package com.mozhumz.balance.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mozhumz.balance.model.entity.CustomerBalanceLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mozhumz.balance.model.qo.BalanceLogQo;
import com.mozhumz.balance.model.vo.CustomerBalanceLogVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 客户余额变动表（充值 消费） Mapper 接口
 * </p>
 *
 * @author lshaci
 * @since 2019-05-27
 */
public interface ICustomerBalanceLogMapper extends BaseMapper<CustomerBalanceLog> {
    IPage<CustomerBalanceLogVO> findBalanceLogList(Page page, @Param("balanceLogQo") BalanceLogQo balanceLogQo);
}
