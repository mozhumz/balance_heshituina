package com.mozhumz.balance.feign;


import com.hyj.util.web.JsonResponse;
import com.mozhumz.balance.feign.entity.dto.CheckTokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author caijiang
 * @date 2018/3/8
 */
@FeignClient(value = "zuul")

public interface IZuulFeign {


    /**
     * 根据工号查询用户所在部门
     * @return
     */
    @RequestMapping(value = "/api/login/checkToken" ,method = RequestMethod.POST)
    JsonResponse checkToken(@RequestBody CheckTokenDto checkTokenDto);

}


