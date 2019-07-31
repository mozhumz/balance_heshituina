package com.mozhumz.balance.model.qo;

import com.hyj.util.param.CheckParamsUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserQo extends BaseQo {
    @ApiModelProperty(value = "用户账号或姓名或手机号")
    private String keyword;

    private Long id;

    private String roleName;

    private String roleIdStr;

    private Integer state;


    public void setKeyword(String keyword) {
        if(CheckParamsUtil.check(keyword))
            this.keyword = "%"+keyword+"%";
    }
}
