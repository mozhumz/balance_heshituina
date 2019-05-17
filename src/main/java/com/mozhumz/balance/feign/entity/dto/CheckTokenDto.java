package com.mozhumz.balance.feign.entity.dto;

import lombok.Data;

/**
 * @author huyuanjia
 * @date 2019/5/8 10:30
 */
@Data
public class CheckTokenDto {
    private String OutUrl;
    private String sessionId;

    private String token;
}
