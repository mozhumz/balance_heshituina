package com.mozhumz.balance.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author lshaci
 * @since 2019-05-05
 */
@Data
//@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="User对象", description="用户表")
public class SessionUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "拥有的角色id 对应t_role 如',1,2,3,'")
    private String roleIdStr;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    @ApiModelProperty(value = "1 正常 2禁用")
    private Integer state;

    private String token;


}
