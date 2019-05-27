package com.mozhumz.balance.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
@RequestMapping("/api/login")
public class BalanceController {
    @Resource
    private HttpServletRequest request;
    @Resource
    HttpServletResponse response;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Value("${urlPre.web}")
    private String indexUrl;


    @ApiOperation(value = "导入Excel-添加用户余额记录")
    @RequestMapping(value = "/addCustomerBalance", method = RequestMethod.POST)
    public JsonResponse addCustomerBalance(@RequestBody MultipartFile file)  {
        return JsonResponse.success(null);

    }

    @ApiOperation(value = "退出")
    @RequestMapping(value = "/logOut", method = RequestMethod.POST)
    public JsonResponse logOut() {
        request.getSession().invalidate();
        return JsonResponse.success(null);

    }


}
