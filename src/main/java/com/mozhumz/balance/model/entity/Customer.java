package com.mozhumz.balance.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lshaci
 * @since 2019-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_customer")
@ApiModel(value="Customer对象", description="")
public class Customer extends Model<Customer> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "客户姓名")
    private String name;

    @ApiModelProperty(value = "客户手机")
    private String phone;

    @ApiModelProperty(value = "客户性别 1 男 2 女")
    private Boolean gender;

    @ApiModelProperty(value = "客户生日")
    @TableField("birthDate")
    private LocalDateTime birthDate;

    @TableField("createDate")
    private LocalDateTime createDate;

    @TableField("updateDate")
    private LocalDateTime updateDate;

    private Boolean state;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
