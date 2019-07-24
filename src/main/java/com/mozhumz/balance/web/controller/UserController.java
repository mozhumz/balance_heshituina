package com.mozhumz.balance.web.controller;

import com.hyj.util.web.JsonResponse;
import com.mozhumz.balance.model.dto.SessionUser;
import com.mozhumz.balance.model.qo.UserQo;
import com.mozhumz.balance.service.IUserService;
import com.mozhumz.balance.utils.SessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author huyuanjia
 * @date 2019/4/2 16:55
 */
@RestController
@Api(value = "登录相关接口", description = "登录相关接口")
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    @Resource
    private HttpServletRequest request;
    @Resource
    HttpServletResponse response;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Value("${urlPre.web}")
    private String indexUrl;
    @Resource
    private IUserService userService;




    @ApiOperation(value = "获取登录用户")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public JsonResponse login() {
        SessionUser sessionUser = SessionUtil.getLoginUser();
        log.info(sessionUser.toString());
        return JsonResponse.success(sessionUser);
    }

    @ApiOperation(value = "退出")
    @RequestMapping(value = "/logOut", method = RequestMethod.GET)
    public JsonResponse logOut() {
        request.getSession().invalidate();
        return JsonResponse.success(null);

    }

    @ApiOperation(value = "获取用户列表")
    @RequestMapping(value = "/getUserList", method = RequestMethod.POST)
    public JsonResponse getUserList(@RequestBody UserQo userQo) {

        return userService.getUserList(userQo);

    }


}
