package com.mozhumz.balance.service.impl;

import com.hyj.util.param.CheckParamsUtil;
import com.mozhumz.balance.enums.ErrorCode;
import com.mozhumz.balance.model.entity.Product;
import com.mozhumz.balance.mapper.IProductMapper;
import com.mozhumz.balance.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lshaci.framework.common.exception.BaseException;
import top.lshaci.framework.web.model.JsonResponse;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 产品项目 服务实现类
 * </p>
 *
 * @author lshaci
 * @since 2019-05-27
 */
@Service
public class ProductServiceImpl extends ServiceImpl<IProductMapper, Product> implements IProductService {
    @Resource
    private IProductMapper productMapper;

    @Override
    @Transactional
    public JsonResponse addProduct(Product product) {
        if(product==null||!CheckParamsUtil.check(product.getName())){
            throw new BaseException(ErrorCode.PARAM_ERR.desc);
        }
        try {
            product.setCreateDate(new Date());
            product.setUpdateDate(new Date());
            productMapper.insert(product);
        }catch (DuplicateKeyException e){
            throw new BaseException(ErrorCode.PRODUCT_NAME_ERR.desc);
        }
        return JsonResponse.success(product);
    }

    @Override
    @Transactional
    public JsonResponse deleteProduct(Product product) {
        if(product==null||!CheckParamsUtil.check(product.getId()+"")){
            throw new BaseException(ErrorCode.PARAM_ERR.desc);
        }
        productMapper.deleteById(product.getId());

        return JsonResponse.success(null);
    }

    @Override
    @Transactional
    public JsonResponse updateProduct(Product product) {
        if(product==null||!CheckParamsUtil.check(product.getId()+"",product.getName())){
            throw new BaseException(ErrorCode.PARAM_ERR.desc);
        }
        productMapper.updateById(product);
        return JsonResponse.success(null);
    }

    @Override
    public JsonResponse getProduct(Product product) {
        if(product==null||!CheckParamsUtil.check(product.getId()+"")){
            throw new BaseException(ErrorCode.PARAM_ERR.desc);
        }

        return JsonResponse.success(productMapper.selectById(product.getId()));
    }

    @Override
    public JsonResponse getAllProductList() {

        return JsonResponse.success(productMapper.selectList(null));
    }
}
