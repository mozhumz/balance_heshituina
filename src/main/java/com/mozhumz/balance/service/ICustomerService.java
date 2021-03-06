package com.mozhumz.balance.service;

import com.hyj.util.web.JsonResponse;
import com.mozhumz.balance.model.entity.Customer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mozhumz.balance.model.qo.CustomerQo;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 客户信息表 服务类
 * </p>
 *
 * @author lshaci
 * @since 2019-05-27
 */
public interface ICustomerService extends IService<Customer> {
    /**
     * 导入Excel-添加客户
     * @param file
     * @return
     */
    JsonResponse addCustomer(MultipartFile file);



    /**
     * 获取客户列表
     * @param customerQo
     * @return
     */
    JsonResponse getCustomerList(CustomerQo customerQo);

    /**
     * 获取客户详情
     * @param customerId
     * @return
     */
    JsonResponse getCustomer(Long customerId);

    JsonResponse updateCustomer(Customer customer);


    boolean saveCustomer(Customer customer,Integer type);

}
