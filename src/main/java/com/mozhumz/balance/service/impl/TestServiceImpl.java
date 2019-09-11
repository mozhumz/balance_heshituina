package com.mozhumz.balance.service.impl;

import com.hyj.util.exception.BaseException;
import com.hyj.util.web.JsonResponse;
import com.mozhumz.balance.feign.IUsermanageFeign;
import com.mozhumz.balance.model.entity.BalanceTest;
import com.mozhumz.balance.service.ITestService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class TestServiceImpl implements ITestService {
    @Resource
    private IUsermanageFeign usermanageFeign;

    @Override
    public JsonResponse fescarTestAdd() {
        System.out.println("com.mozhumz.balance.service.impl.TestServiceImpl.fescarTestAdd-xid:"+ RootContext.getXID());

        usermanageFeign.addManageTest();
        BalanceTest balanceTest=new BalanceTest();
        Date date=new Date();
        balanceTest.setCreateDate(date);
        balanceTest.setUpdateDate(date);
        balanceTest.setStr("balanceTest:"+date.toString());
        balanceTest.insert();
        if(balanceTest.getId()!=null&&balanceTest.getId()>1){
            throw new BaseException("fescar test");
        }
        return JsonResponse.success(balanceTest);
    }
}
