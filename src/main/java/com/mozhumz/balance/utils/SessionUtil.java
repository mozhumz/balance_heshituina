package com.mozhumz.balance.utils;

import com.mozhumz.balance.constant.CommonConstant;
import com.mozhumz.balance.model.dto.SessionUser;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.lshaci.framework.web.exception.LoginException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.Duration;

/**
 * @author huyuanjia
 * @date 2019/5/6 20:37
 */
@Component
@Slf4j
public class SessionUtil {
    public static RedisTemplate redisTemplate;

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
        String token= (String) getSession().getAttribute(CommonConstant.token);
        if(token==null){
            throw new LoginException();
        }
        JSONObject jsonObject= (JSONObject) redisTemplate.opsForValue().get(CommonConstant.globalSessionUser+token);
        SessionUser userDto= (SessionUser) JSONObject.toBean(jsonObject,SessionUser.class);
        return userDto;
    }

    public static void setSessionUser(Long sessionSeconds,SessionUser userDto){
        Duration duration = Duration.ofSeconds(sessionSeconds);
        redisTemplate.opsForValue().set(CommonConstant.globalSessionUser + userDto.getToken(), userDto, duration);
    }
}
