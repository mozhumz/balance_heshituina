package com.mozhumz.balance.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Data
@ApiModel("修改客户密码")
public class ChangePwdDto {
    @ApiModelProperty(value = "客户支付密码")
    private String password;

    @ApiModelProperty(value = "客户支付密码")
    private String oldPwd;

    @ApiModelProperty(value = "客户id")
    private Long id;

    public static void main(String[] args) throws Exception {

    }
}
