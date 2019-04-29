package com.mozhumz.balance.model.entity;

import java.math.BigDecimal;
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
@TableName("t_customer_balance_log")
@ApiModel(value="CustomerBalanceLog对象", description="")
public class CustomerBalanceLog extends Model<CustomerBalanceLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "对应t_customer id")
    @TableField("customerId")
    private Long customerId;

    @ApiModelProperty(value = "客户余额")
    private BigDecimal money;

    @TableField("createDate")
    private LocalDateTime createDate;

    @TableField("updateDate")
    private LocalDateTime updateDate;

    @ApiModelProperty(value = "1正常 2删除")
    private Boolean state;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
