package com.mozhumz.balance.model.qo;

import com.hyj.util.param.CheckParamsUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("消费项目查询")
public class ProductQo extends BaseQo{
    @ApiModelProperty(value = "消费项目id")
    private Long id;

    @ApiModelProperty(value = "消费项目名称")
    private String keyword;


    public void setKeyword(String keyword) {
        if(CheckParamsUtil.check(keyword))
        this.keyword = keyword;
    }
}
