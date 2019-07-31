package com.mozhumz.balance.feign;


import com.hyj.util.web.JsonResponse;
import com.mozhumz.balance.model.dto.SendEmailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @author caijiang
 * @date 2018/3/8
 */
@FeignClient(value = "usermanage")
public interface IUsermanageFeign {


    /**
     * 发送邮箱验证码
     * @return
     */
    @RequestMapping(value = "/api/sys/sendEmailCode" ,method = RequestMethod.POST)
    JsonResponse sendEmailCode(@RequestBody SendEmailDto sendEmailDto);

}

