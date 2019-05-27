package com.mozhumz.balance.utils;


import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author huyuanjia
 * @date 2019/5/27 15:37
 */
public class CommonUtil {

    /**
     * 四舍五入 保留两位小数
     * @param d
     * @return
     */
    public static Double get45(double d){
        return Double.parseDouble(String.format("%.2f", d));
    }

    public static String getIdStr(List ids){
        if(CollectionUtils.isEmpty(ids)){
            return null;
        }
        String idStr=",";
        for(Object id:ids){
            idStr+=id+",";
        }
        return idStr;
    }

}
