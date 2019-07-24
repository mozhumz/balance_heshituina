package com.mozhumz.balance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hyj.util.web.JsonResponse;
import com.mozhumz.balance.model.entity.User;
import com.mozhumz.balance.model.qo.UserQo;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author lshaci
 * @since 2019-04-29
 */
public interface IUserService extends IService<User> {

    JsonResponse getUserList(UserQo userQo);
}
