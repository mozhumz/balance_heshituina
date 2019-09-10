package com.mozhumz.balance.web.controller;

import com.hyj.util.exception.BaseException;
import com.hyj.util.param.CheckParamsUtil;
import com.hyj.util.web.JsonResponse;
import com.mozhumz.balance.constant.CommonConstant;
import com.mozhumz.balance.enums.ErrorCode;
import com.mozhumz.balance.feign.IUsermanageFeign;
import com.mozhumz.balance.model.dto.BalanceDto;
import com.mozhumz.balance.model.dto.SendEmailDto;
import com.mozhumz.balance.model.entity.Customer;
import com.mozhumz.balance.model.entity.Product;
import com.mozhumz.balance.model.qo.BalanceLogQo;
import com.mozhumz.balance.model.qo.CustomerQo;
import com.mozhumz.balance.model.qo.ProductQo;
import com.mozhumz.balance.service.ICustomerBalanceLogService;
import com.mozhumz.balance.service.ICustomerService;
import com.mozhumz.balance.service.IProductService;
import com.mozhumz.balance.utils.EmailUtil;
import com.mozhumz.balance.utils.SessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@Slf4j
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
    @Resource
    private IUsermanageFeign usermanageFeign;




    @ApiOperation(value = "添加服务项目")
    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public JsonResponse addProduct(@RequestBody Product product) {

        return productService.addProduct(product);

    }

    @ApiOperation(value = "删除服务项目")
    @RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
    public JsonResponse deleteProduct(@RequestBody Product product) {

        return productService.deleteProduct(product);

    }

    @ApiOperation(value = "修改服务项目")
    @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
    public JsonResponse updateProduct(@RequestBody Product product) {

        return productService.updateProduct(product);

    }

    @ApiOperation(value = "获取所有服务项目")
    @RequestMapping(value = "/getAllProductList", method = RequestMethod.POST)
    public JsonResponse getAllProductList(@RequestBody ProductQo productQo) {

        return productService.getAllProductList(productQo);
    }

    @ApiOperation(value = "获取服务项目")
    @RequestMapping(value = "/getProduct", method = RequestMethod.POST)
    public JsonResponse getProduct(@RequestBody Product product) {
        return productService.getProduct(product);

    }

    @ApiOperation(value = "导入Excel-添加客户余额记录")
    @RequestMapping(value = "/addCustomerList", method = RequestMethod.POST)
    public JsonResponse addCustomerList(@RequestBody MultipartFile file) {
        return customerService.addCustomer(file);

    }

    @ApiOperation(value = "添加客户")
    @RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
    public JsonResponse addCustomer(@RequestBody Customer customer) {
        boolean f=customerService.saveCustomer(customer,null);
        if(f){
            throw new BaseException(ErrorCode.CUSTOMER_MONEY_ERR.code,ErrorCode.CUSTOMER_MONEY_ERR.desc);
        }
        return JsonResponse.success(customer);

    }

    @ApiOperation(value = "获取客户详情")
    @RequestMapping(value = "/getCustomer", method = RequestMethod.POST)
    public JsonResponse getCustomer(@RequestBody Customer customer) {
        return customerService.getCustomer(customer.getId());

    }

    @ApiOperation(value = "修改客户信息")
    @RequestMapping(value = "/updateCustomer", method = RequestMethod.POST)
    public JsonResponse updateCustomer(@RequestBody Customer customer) {
        return customerService.updateCustomer(customer);

    }

    @ApiOperation(value = "获取客户列表")
    @RequestMapping(value = "/getCustomerList", method = RequestMethod.POST)
    public JsonResponse getCustomerList(@RequestBody CustomerQo customerQo) {
        return customerService.getCustomerList(customerQo);

    }

    @ApiOperation(value = "客户充值/消费")
    @RequestMapping(value = "/addBalanceLog", method = RequestMethod.POST)
    public JsonResponse addBalanceLog(@RequestBody BalanceDto balanceDto) {
        return customerBalanceLogService.addBalanceLog(balanceDto);
    }


    @ApiOperation(value = "获取客户充值/消费记录列表")
    @RequestMapping(value = "/getBalanceLogList", method = RequestMethod.POST)
    public JsonResponse getBalanceLogList(@RequestBody BalanceLogQo balanceLogQo) {
        return customerBalanceLogService.getBalanceLogList(balanceLogQo);
    }


    @ApiOperation(value = "发送验证码")
    @RequestMapping(value = "/sendEmailCode", method = RequestMethod.POST)
    public JsonResponse sendEmailCode(@RequestBody SendEmailDto sendEmailDto) {
        log.info("sendEmailCode start");
        if(!CheckParamsUtil.check(sendEmailDto.getReceiveEmail(),sendEmailDto.getCustomerId()+"")){
            throw new BaseException(ErrorCode.PARAM_ERR.desc);
        }
        sendEmailDto.setTitle(CommonConstant.emailCodeTitle);
        sendEmailDto.setKey(CommonConstant.customerCode+sendEmailDto.getCustomerId()+sendEmailDto.getReceiveEmail());
        sendEmailDto.setContent(EmailUtil.getEmailCode(6));

        return usermanageFeign.sendEmailCode(sendEmailDto);
    }

    @ApiOperation(value = "获取验证码")
    @RequestMapping(value = "/getEmailCode", method = RequestMethod.POST)
    public JsonResponse getEmailCode(@RequestBody SendEmailDto sendEmailDto) {
        if(!CheckParamsUtil.check(sendEmailDto.getReceiveEmail(),sendEmailDto.getCustomerId()+"")){
            throw new BaseException(ErrorCode.PARAM_ERR.desc);
        }
        return JsonResponse.success(SessionUtil.getRedisV(CommonConstant.customerCode+sendEmailDto.getCustomerId()
                        +sendEmailDto.getReceiveEmail())
                );
    }


}
