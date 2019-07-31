package com.mozhumz.balance.model.dto;

import com.hyj.util.anno.IsNeed;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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

    private List<ProductUserDto> productUserList;

    @IsNeed(flag = true)
    private Integer type;

    private String remark;
    @IsNeed(flag = true)
    @ApiModelProperty(value = "操作人userId")
    private Long doUserId;

    @ApiModelProperty(value = "客户密码")
    private String password;

    @ApiModelProperty(value = "操作人密码")
    @IsNeed(flag = true)
    private String doPassword;
}
