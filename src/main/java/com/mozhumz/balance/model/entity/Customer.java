package com.mozhumz.balance.model.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import com.hyj.util.anno.IsNeed;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 客户信息表
 * </p>
 *
 * @author lshaci
 * @since 2019-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_customer")
@ApiModel(value="Customer对象", description="客户信息表")
@ExcelTarget("Customer")
public class Customer extends Model<Customer> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "客户姓名")
    @Excel(name = "姓名")
    @IsNeed(flag = true)
    private String name;

    @ApiModelProperty(value = "客户手机")
     @Excel(name = "手机")
    @IsNeed(flag = true)
    private String phone;

    @ApiModelProperty(value = "客户余额")
    @Excel(name = "余额")
    @IsNeed(flag = true)
    private Double money;

    @ApiModelProperty(value = "客户支付密码")
    private String password;
    @ApiModelProperty(value = "客户支付密码是否为初始密码 1是 2否")
    private Integer is0pwd;

    @ApiModelProperty(value = "客户编号")
    @TableField("customerNo")
    @Excel(name = "编号")
    @IsNeed(flag = true)
    private String customerNo;

    @ApiModelProperty(value = "客户性别 1 男 2 女")
    private Integer gender;

    @ApiModelProperty(value = "客户生日")
    @TableField("birthDate")
    private Date birthDate;

    @ApiModelProperty(value = "备注")
    private String remark;

    @TableField("createDate")
    private Date createDate;

    @TableField("updateDate")
    private Date updateDate;

    private Integer state;

    private String email;

    @TableField(exist = false)
    @ApiModelProperty(value = "邮箱验证码")
    private String emailCode;

    @TableField(exist = false)
    @ApiModelProperty(value = "操作密码")
    private String doPassword;


    @TableField(exist = false)
    @ApiModelProperty(value = "添加人名字")
    private String doName;

    @TableField("doUserId")
    @ApiModelProperty(value = "添加人id")
    private Long doUserId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
