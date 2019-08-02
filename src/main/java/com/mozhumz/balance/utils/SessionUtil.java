package com.mozhumz.balance.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hyj.util.config.GsonConfig;
import com.hyj.util.exception.BaseException;
import com.hyj.util.param.CheckParamsUtil;
import com.hyj.util.web.GsonUtil;
import com.mozhumz.balance.constant.CommonConstant;
import com.mozhumz.balance.enums.ErrorCode;
import com.mozhumz.balance.model.dto.SessionUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.util.Date;

/**
 * @author huyuanjia
 * @date 2019/5/6 20:37
 */
@Component
@Slf4j
public class SessionUtil {
    public static RedisTemplate redisTemplate;
    public static Gson gson = GsonUtil.gson;



    @Resource
    public void setRedisTemplate(RedisTemplate redisTemplate){
        SessionUtil.redisTemplate=redisTemplate;
    }

    /**
     * 获取session
     * @return
     */
    public static HttpSession getSession() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return requestAttributes.getRequest().getSession();
    }

    /**
     * 获取登录用户
     * @return
     */
    public static SessionUser getLoginUser(){
        String token= (String) getSession().getAttribute(CommonConstant.balanceToken);
        if(!CheckParamsUtil.check(token)){
            throw new BaseException(ErrorCode.LOGIN_EXP_ERR.desc,ErrorCode.LOGIN_EXP_ERR.code);
        }
        String json= (String) redisTemplate.opsForValue().get(CommonConstant.globalSessionUser+token);
        if(!CheckParamsUtil.check(json)){
            throw new BaseException(ErrorCode.LOGIN_EXP_ERR.desc,ErrorCode.LOGIN_EXP_ERR.code);
        }
        return gson.fromJson(json,new TypeToken<SessionUser>(){}.getType());
    }

    public static void setSessionUser(Long sessionSeconds,SessionUser userDto){
        Duration duration = Duration.ofSeconds(sessionSeconds);
        redisTemplate.opsForValue().set(CommonConstant.globalSessionUser +userDto.getToken(),
                gson.toJson(userDto),duration);
    }

    /**
     * 设置redis KV
     * @param k
     * @param v
     * @param seconds 失效秒值
     */
    public static void setRedisKV(String k,String v,long seconds){
        Duration duration = Duration.ofSeconds(seconds);
        redisTemplate.opsForValue().set(k,v,duration);
    }

    /**
     * 根据key获取redis V
     * @param k
     * @return
     */
    public static String getRedisV(String k){
        return (String) redisTemplate.opsForValue().get(k);
    }

    /**
     * 根据客户邮箱和客户id获取验证码
     * @param receiveEmail
     * @param customerId
     * @return
     */
    public static String getEmailCode(String receiveEmail,Long customerId){
        return getRedisV(CommonConstant.customerCode+customerId+receiveEmail);
    }
}
