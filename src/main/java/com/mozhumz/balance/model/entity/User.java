package com.mozhumz.balance.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author lshaci
 * @since 2019-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user")
@ApiModel(value="User对象", description="用户表")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "扣款密码")
    @TableField("balancePwd")
    private String balancePwd;

    @ApiModelProperty(value = "拥有的角色id 对应t_role 如',1,2,3,'")
    @TableField("roleIdStr")
    private String roleIdStr;

    @TableField("createDate")
    private Date createDate;

    @TableField("updateDate")
    private Date updateDate;

    @ApiModelProperty(value = "1 正常 2禁用")
    private Integer state;

    @ApiModelProperty(value = "用户姓名")
    @TableField("realName")
    private String realName;

    @TableField("phone")
    @ApiModelProperty(value = "用户手机")
    private String phone;

    private Integer gender;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
