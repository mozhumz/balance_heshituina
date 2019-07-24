package com.mozhumz.balance.model.vo;

import com.mozhumz.balance.model.entity.Product;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.StringJoiner;

@ApiModel("历史消费-消费情况")
@Data
public class ProductUserVO {
    private Long balanceLogId;
    private Long userId;
    private String realName;

    private List<Product> productList;

    private String productStr;

    public List<Product> getProductList() {
        if(!CollectionUtils.isEmpty(productList)){
            StringJoiner stringJoiner=new StringJoiner(",");
            productList.forEach(item->stringJoiner.add(item.getName()+""));
            productStr=stringJoiner.toString();
        }
        return productList;

    }



}
