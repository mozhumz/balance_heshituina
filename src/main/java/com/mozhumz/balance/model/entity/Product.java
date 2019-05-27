package com.mozhumz.balance.model.entity;

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
 * 产品项目
 * </p>
 *
 * @author lshaci
 * @since 2019-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_product")
@ApiModel(value="Product对象", description="产品项目")
public class Product extends Model<Product> {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    @TableField("createDate")
    private Date createDate;

    @TableField("updateDate")
    private Date updateDate;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
