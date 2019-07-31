package com.mozhumz.balance.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("发送邮件")
public class SendEmailDto {
    @ApiModelProperty(value = "发件人邮箱")
    private String sendEmail;
    @ApiModelProperty(value = "发件人授权码")
    //vtkkubmpydiqcagi
    private String sendPwd;
    @ApiModelProperty(value = "收件人邮箱")
    private String receiveEmail;

    @ApiModelProperty(value = "邮件标题")
    private String title;
    @ApiModelProperty(value = "邮件内容")
    private String content;

    @ApiModelProperty(value = "存入redis的key")
    private String key;

    @ApiModelProperty(value = "客户id")
    private Long customerId;
}
