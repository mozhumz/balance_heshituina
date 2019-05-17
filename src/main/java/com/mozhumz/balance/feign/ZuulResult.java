package com.mozhumz.balance.feign;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.mozhumz.balance.feign.entity.dto.CheckTokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.lshaci.framework.common.exception.BaseException;
import top.lshaci.framework.web.model.JsonResponse;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * @author caijiang
 * @date 2018/3/8
 */
@Slf4j
@Component
public class ZuulResult {

    @Resource
    private IZuulFeign zuulFeign;

    private Gson gson = getGson();

    public Gson getGson() {
        GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
        return builder.create();
    }


    /**
     * token校验
     *
     * @return
     */
    public boolean checkToken(CheckTokenDto checkTokenDto) {
        JsonResponse result = zuulFeign.checkToken(checkTokenDto);
        boolean flag=false;
        if (result != null && result.isStatus()) {
            if (result.getData() != null) {
                String str = gson.toJson(result.getData());
                if (str != null) {
                    flag = gson.fromJson(str, new TypeToken<Boolean>() {
                    }.getType());
                }
            }
        } else {
            log.error("远程调用网关接口出错");
            throw new BaseException("远程调用网关接口出错");
        }
        return flag;
    }

}
