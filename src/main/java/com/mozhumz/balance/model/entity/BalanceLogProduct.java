package com.mozhumz.balance.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
 * 客户单个消费项目表
 * </p>
 *
 * @author lshaci
 * @since 2019-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_balance_log_product")
@ApiModel(value="BalanceLogProduct对象", description="客户单个消费项目表")
public class BalanceLogProduct extends Model<BalanceLogProduct> {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "t_balance_log id")
    @TableField("balanceLogId")
    private Long balanceLogId;

    @ApiModelProperty(value = "t_balance_log_user id")
    @TableField("balanceLogUserId")
    private Long balanceLogUserId;

    @ApiModelProperty(value = "t_product id")
    @TableField("productId")
    private Long productId;

    @ApiModelProperty(value = "t_customer id")
    @TableField("customerId")
    private Long customerId;

    @ApiModelProperty(value = "t_user id")
    @TableField("userId")
    private Long userId;


    @TableField("createDate")
    private Date createDate;

    @TableField("updateDate")
    private Date updateDate;

    private Integer state;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
