package com.mozhumz.balance.web.aop;

import com.mozhumz.balance.anno.SkipLoginCheckAnno;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.lang.reflect.Method;


/*
 */
@Configuration
@Aspect
@EnableAspectJAutoProxy
@Slf4j
public class UserAop {


//    @Pointcut("execution(* com.hyj.demo.web.controller.*.*(..)))")
//    public void userControllerPoingtCut(){}
//
//    /**
//     * 拦截whut中的控制器，将当前用户写入数据库
//     */
//    @Before("userControllerPoingtCut()")
//    @Transactional
//    public void checkMutualEvaluAopAuther() {
////        User user = userRepository.findByUserName(SessionUtil.getCurrentmentSn());
////        if (user == null) {
////            userRepository.save(SessionUtil.getCurrentUser());
////        }
//    }

    /**
     * 前置通知：目标方法执行之前执行以下方法体的内容
     * controller.*.*(..)) 第一个*表示类 第二个*表示方法  (..)表示方法内部实现
     *
     * @param joinPoint
     */
//    @Before("execution(* com.xdbigdata.develop.web.controller.*.*(..))")
//    public void beforeMethod(JoinPoint joinPoint) {
//        checkLogin(joinPoint);
//    }

    /**
     * 登录检查
     *
     * @param joinPoint
     */
//    @Before("execution(* com.xdbigdata.user_manage_admin.web.controller.*.*(..))")
//    public void checkLogin(JoinPoint joinPoint) {
//        Signature signature = joinPoint.getSignature();
//        MethodSignature methodSignature = (MethodSignature) signature;
//        //被拦截的方法
//        Method targetMethod = methodSignature.getMethod();
//        SkipLoginCheckAnno anno = targetMethod.getAnnotation(SkipLoginCheckAnno.class);
//        if (anno != null) {
//            return;
//        }
//        //登录检查
//        SessionUtil.getCurrentUser();
//    }

//    @Before("execution(* com.xdbigdata.develop.web.controller.dataexport.*.*(..))")
//    public void beforeMethod2(JoinPoint joinPoint) {
//        checkLogin(joinPoint);
//    }


}
