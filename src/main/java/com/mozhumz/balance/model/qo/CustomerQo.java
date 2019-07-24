package com.mozhumz.balance.model.qo;

import com.hyj.util.param.CheckParamsUtil;
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

    public void setKeyword(String keyword) {
        if(CheckParamsUtil.check(keyword)){

            this.keyword = "%"+keyword+"%";
        }
    }
}
