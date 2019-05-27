package com.mozhumz.balance.model.dto;

import com.hyj.util.anno.IsNeed;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author huyuanjia
 * @date 2019/5/27 11:37
 */
@Data
@ApiModel("客户余额变动")
public class BalanceDto {
    @IsNeed(flag = true)
    private Long customerId;
    @IsNeed(flag = true)
    private Double money;

    private List<Long> productIds;

    @IsNeed(flag = true)
    private Integer type;

    private String remark;
}
