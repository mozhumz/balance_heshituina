package com.mozhumz.balance.service;

import com.mozhumz.balance.model.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import top.lshaci.framework.web.model.JsonResponse;

/**
 * <p>
 * 产品项目 服务类
 * </p>
 *
 * @author lshaci
 * @since 2019-05-27
 */
public interface IProductService extends IService<Product> {
    JsonResponse addProduct(Product product);

    JsonResponse deleteProduct(Product product);

    JsonResponse updateProduct(Product product);

    JsonResponse getProduct(Product product);

    JsonResponse getAllProductList();
}
