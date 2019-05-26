package com.mozhumz.balance.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.lshaci.framework.web.model.JsonResponse;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author huyuanjia
 * @date 2019/4/2 16:55
 */
@RestController
    @Api(value = "测试相关接口", description = "测试相关接口")
@RequestMapping("/api/login")
public class LoginController {
    @Resource
    private HttpServletRequest request;
    @Resource
    HttpServletResponse response;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Value("${urlPre.web}")
    private String indexUrl;


    @ApiOperation(value = "登录跳转")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonResponse login() throws IOException {
        response.sendRedirect(indexUrl);
        return JsonResponse.success(null);

    }

    @ApiOperation(value = "退出")
    @RequestMapping(value = "/logOut", method = RequestMethod.POST)
    public JsonResponse logOut() {
        request.getSession().invalidate();
        return JsonResponse.success(null);

    }


}
