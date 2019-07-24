package com.mozhumz.balance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyj.util.exception.BaseException;
import com.hyj.util.param.CheckParamsUtil;
import com.hyj.util.web.GsonUtil;
import com.hyj.util.web.JsonResponse;
import com.mozhumz.balance.mapper.IRoleMapper;
import com.mozhumz.balance.mapper.IUserMapper;
import com.mozhumz.balance.model.entity.Role;
import com.mozhumz.balance.model.entity.User;
import com.mozhumz.balance.model.qo.UserQo;
import com.mozhumz.balance.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringJoiner;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author lshaci
 * @since 2019-04-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<IUserMapper, User> implements IUserService {

    @Resource
    private IUserMapper userMapper;
    @Resource
    private IRoleMapper roleMapper;


    @Override
    public JsonResponse getUserList(UserQo userQo) {
        if(CheckParamsUtil.check(userQo.getRoleName())){
            QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("name",userQo.getRoleName());
            Role role=roleMapper.selectOne(queryWrapper);
            if(role!=null){
                userQo.setRoleIdStr("%,"+role.getId()+",%");
            }
        }
        return JsonResponse.success(userMapper.findUserVOList(userQo));
    }
}
