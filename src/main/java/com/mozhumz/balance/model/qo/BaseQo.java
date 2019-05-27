package com.mozhumz.balance.model.qo;

import lombok.Data;

import java.util.Date;

/**
 * @author huyuanjia
 * @date 2019/5/27 14:16
 */
@Data
public class BaseQo {
    private int size=20;
    private int page=1;
    private Date startDate;
    private Date endDate;
}
