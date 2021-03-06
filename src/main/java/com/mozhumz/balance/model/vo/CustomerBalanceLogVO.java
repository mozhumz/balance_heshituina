package com.mozhumz.balance.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.mozhumz.balance.model.entity.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 客户余额变动表（充值 消费）
 * </p>
 *
 * @author lshaci
 * @since 2019-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_customer_balance_log")
@ApiModel(value="CustomerBalanceLog对象", description="客户余额变动表（充值 消费）")
public class CustomerBalanceLogVO extends Model<CustomerBalanceLogVO> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "对应t_customer id")
    @TableField("customerId")
    private Long customerId;

    private String customerName;

    private String phone;

    private String customerNo;



    @ApiModelProperty(value = "客户余额")
    private Double money;


    @ApiModelProperty(value = "余额变动类型：1 消费 2 充值")
    private Integer type;

    @TableField("createDate")
    private Date createDate;

    @TableField("updateDate")
    private Date updateDate;

    @ApiModelProperty(value = "1正常 2删除")
    private Integer state;

    @ApiModelProperty(value = "备注")
    private String remark;

    @TableField("doUserName")
    @ApiModelProperty(value = "操作账号")
    private String doUserName;
    @TableField("doName")
    @ApiModelProperty(value = "实际操作人名字")
    private String doName;

    private String doUserId;

    @ApiModelProperty(value = "消费项目列表")
    private List<ProductUserVO> productUserList;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
