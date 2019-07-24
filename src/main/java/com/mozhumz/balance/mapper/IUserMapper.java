package com.mozhumz.balance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mozhumz.balance.model.entity.User;
import com.mozhumz.balance.model.qo.UserQo;
import com.mozhumz.balance.model.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author lshaci
 * @since 2019-04-29
 */
public interface IUserMapper extends BaseMapper<User> {
    int addOne(User user);
    List<UserVO> findUserVOList(@Param("qo") UserQo userQo);
}
