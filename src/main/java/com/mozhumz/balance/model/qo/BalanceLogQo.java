package com.mozhumz.balance.model.qo;

import com.hyj.util.param.CheckParamsUtil;
import com.mozhumz.balance.utils.CommonUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author huyuanjia
 * @date 2019/5/27 14:18
 */
@Data
@ApiModel("客户余额历史查询")
public class BalanceLogQo extends BaseQo {
    @ApiModelProperty(value = "客户手机/名字/编号")
    private String keyword;
    @ApiModelProperty(value = "余额变动类型：1 消费 2 充值")
    private Integer type;

    @ApiModelProperty(value = "项目id列表")
    private List<Long>productIds;

    private String productIdStr;

    public void setKeyword(String keyword) {
        if(CheckParamsUtil.check(keyword))
        this.keyword = "%"+keyword+"%";
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
        productIdStr= CommonUtil.getIdStr(productIds);
    }
}
