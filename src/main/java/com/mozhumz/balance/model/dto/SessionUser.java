package com.mozhumz.balance.model.dto;

import com.mozhumz.balance.model.entity.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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

    private Date createDate;

    private Date updateDate;

    @ApiModelProperty(value = "1 正常 2禁用")
    private Integer state;

    private String token;

    private List<Role> roleList;

    @ApiModelProperty(value = "当前登录角色")
    private Role role;




}
