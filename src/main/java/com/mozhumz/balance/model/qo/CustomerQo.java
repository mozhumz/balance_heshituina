package com.mozhumz.balance.model.qo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author huyuanjia
 * @date 2019/5/27 14:28
 */
@Data
public class CustomerQo extends BaseQo{
    @ApiModelProperty(value = "客户手机/名字/编号")
    private String keyword;

}
