package com.mozhumz.balance.service;

import com.mozhumz.balance.model.dto.BalanceDto;
import com.mozhumz.balance.model.entity.CustomerBalanceLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mozhumz.balance.model.qo.BalanceLogQo;
import top.lshaci.framework.web.model.JsonResponse;

/**
 * <p>
 * 客户余额变动表（充值 消费） 服务类
 * </p>
 *
 * @author lshaci
 * @since 2019-05-27
 */
public interface ICustomerBalanceLogService extends IService<CustomerBalanceLog> {
    /**
     * 充值/消费
     * @param balanceDto
     * @return
     */
    JsonResponse addBalanceLog(BalanceDto balanceDto);

    /**
     * 获取客户消费/充值历史列表
     * @param balanceLogQo
     * @return
     */
    JsonResponse getBalanceLogList(BalanceLogQo balanceLogQo);
}
