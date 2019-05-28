package com.mozhumz.balance.web.controller;

import com.mozhumz.balance.model.dto.BalanceDto;
import com.mozhumz.balance.model.entity.Customer;
import com.mozhumz.balance.model.entity.Product;
import com.mozhumz.balance.model.qo.BalanceLogQo;
import com.mozhumz.balance.model.qo.CustomerQo;
import com.mozhumz.balance.service.ICustomerBalanceLogService;
import com.mozhumz.balance.service.ICustomerService;
import com.mozhumz.balance.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.lshaci.framework.web.model.JsonResponse;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author huyuanjia
 * @date 2019/4/2 16:55
 */
@RestController
@Api(value = "余额相关接口", description = "余额相关接口")
@RequestMapping("/api/balance")
public class BalanceController {
    @Resource
    private HttpServletRequest request;
    @Resource
    HttpServletResponse response;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Value("${urlPre.web}")
    private String indexUrl;
    @Resource
    private ICustomerService customerService;
    @Resource
    private ICustomerBalanceLogService customerBalanceLogService;
    @Resource
    private IProductService productService;


    @ApiOperation(value = "退出")
    @RequestMapping(value = "/logOut", method = RequestMethod.GET)
    public JsonResponse logOut() {
        request.getSession().invalidate();
        return JsonResponse.success(null);

    }


    @ApiOperation(value = "添加服务项目")
    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public JsonResponse addProduct(@RequestBody Product product) {

        return JsonResponse.success(productService.addProduct(product));

    }

    @ApiOperation(value = "删除服务项目")
    @RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
    public JsonResponse deleteProduct(@RequestBody Product product) {

        return JsonResponse.success(productService.deleteProduct(product));

    }

    @ApiOperation(value = "修改服务项目")
    @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
    public JsonResponse updateProduct(@RequestBody Product product) {

        return JsonResponse.success(productService.updateProduct(product));

    }

    @ApiOperation(value = "获取所有服务项目")
    @RequestMapping(value = "/getAllProductList", method = RequestMethod.POST)
    public JsonResponse getAllProductList() {

        return JsonResponse.success(productService.getAllProductList());

    }

    @ApiOperation(value = "获取服务项目")
    @RequestMapping(value = "/getProduct", method = RequestMethod.POST)
    public JsonResponse getProduct(@RequestBody Product product) {
        return JsonResponse.success(productService.getProduct(product));

    }

    @ApiOperation(value = "导入Excel-添加用户余额记录")
    @RequestMapping(value = "/addCustomerList", method = RequestMethod.POST)
    public JsonResponse addCustomerList(@RequestBody MultipartFile file) {
        return JsonResponse.success(customerService.addCustomer(file));

    }

    @ApiOperation(value = "获取客户详情")
    @RequestMapping(value = "/getCustomer", method = RequestMethod.POST)
    public JsonResponse getCustomer(@RequestBody Long id) {
        return JsonResponse.success(customerService.getCustomer(id));

    }

    @ApiOperation(value = "修改客户信息")
    @RequestMapping(value = "/updateCustomer", method = RequestMethod.POST)
    public JsonResponse updateCustomer(@RequestBody Customer customer) {
        return JsonResponse.success(customerService.updateCustomer(customer));

    }

    @ApiOperation(value = "获取客户列表")
    @RequestMapping(value = "/getCustomerList", method = RequestMethod.POST)
    public JsonResponse getCustomerList(@RequestBody CustomerQo customerQo) {
        return JsonResponse.success(customerService.getCustomerList(customerQo));

    }

    @ApiOperation(value = "客户充值/消费")
    @RequestMapping(value = "/addBalanceLog", method = RequestMethod.POST)
    public JsonResponse addBalanceLog(@RequestBody BalanceDto balanceDto) {
        return JsonResponse.success(customerBalanceLogService.addBalanceLog(balanceDto));
    }


    @ApiOperation(value = "客户充值/消费")
    @RequestMapping(value = "/getBalanceLogList", method = RequestMethod.POST)
    public JsonResponse getBalanceLogList(@RequestBody BalanceLogQo balanceLogQo) {
        return JsonResponse.success(customerBalanceLogService.getBalanceLogList(balanceLogQo));
    }


}
