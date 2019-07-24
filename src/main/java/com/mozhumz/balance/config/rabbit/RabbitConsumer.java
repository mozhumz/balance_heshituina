package com.mozhumz.balance.config.rabbit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyj.util.web.GsonUtil;
import com.mozhumz.balance.enums.SaveUserEnum;
import com.mozhumz.balance.mapper.IUserMapper;
import com.mozhumz.balance.mapper.IUserRoleMapper;
import com.mozhumz.balance.model.dto.UserRbtDto;
import com.mozhumz.balance.model.entity.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description rabbitmq消费者 @RabbitListener(queues = "simpleMsg") 监听名simpleMsg的队列
 */
@Component
@Slf4j
public class RabbitConsumer {

    @Autowired
    private AmqpTemplate rabbitmqTemplate;
    @Resource
    private IUserRoleMapper userRoleMapper;
    @Resource
    private IUserMapper userMapper;



    /**
     * 消息消费
     *
     * @RabbitHandler 代表此方法为接受到消息后的处理方法
     */
    @RabbitListener(queues = "balance-saveUserQueue")
    @Transactional
    public void recieved(Message msg) {
        log.info("RabbitConsumer recieved message:" + msg);
        if(msg.getBody()==null){
            return;
        }
        UserRbtDto userRbtDto=GsonUtil.gson.fromJson(new String(msg.getBody()), UserRbtDto.class);
        if(userRbtDto!=null){
            if(SaveUserEnum.add.code==userRbtDto.getType()){
                //新增用户
                if(userRbtDto.getUser()!=null){
                    userMapper.addOne(userRbtDto.getUser());
                }
                if(!CollectionUtils.isEmpty(userRbtDto.getRoleList())){
                    userRbtDto.getRoleList().forEach(item->userRoleMapper.addOne(item));
                }
            }else {
                //修改用户
                userRbtDto.getUser().updateById();

                if(!CollectionUtils.isEmpty(userRbtDto.getRoleList())){
                    //修改角色
                    QueryWrapper<UserRole> queryWrapper=new QueryWrapper();
                    queryWrapper.eq("userId",userRbtDto.getUser().getId());
                    userRoleMapper.delete(queryWrapper);
                    for(UserRole userRole:userRbtDto.getRoleList()){
                        userRole.setCreateDate(new Date());
                        userRole.setUpdateDate(new Date());
                        userRoleMapper.insert(userRole);
                    }
                }
            }
        }
    }




    //字节码转化为对象
    public  Object getObjectFromBytes(byte[] objBytes) throws Exception {
        if (objBytes == null || objBytes.length == 0) {
            return null;
        }
        ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
        ObjectInputStream oi = new ObjectInputStream(bi);
        return oi.readObject();
    }
}
