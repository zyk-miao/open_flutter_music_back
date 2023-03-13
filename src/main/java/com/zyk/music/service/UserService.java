package com.zyk.music.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.jwt.JWTUtil;
import com.zyk.music.base.ReturnRes;
import com.zyk.music.entity.User;
import com.zyk.music.entity.vo.UserBo;
import com.zyk.music.mapper.UserMapper;
import com.zyk.music.wrapper.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public ReturnRes<Object> login(User user) {
        User one = userMapper.findOne(new UserQuery().where.username().eq(user.getUsername()).and.password().eq(user.getPassword()).end());
        if (one != null) {
            one.setPassword(null);
            UserBo userBo = new UserBo();
            BeanUtil.copyProperties(one, userBo, false);
            Map<String,Object> map=new HashMap<>();
            map.put("userId", userBo.getId());
            String token = JWTUtil.createToken(map, userBo.getId().getBytes());
            userBo.setToken(token);
            return ReturnRes.success("登录成功", userBo);
        } else {
            return ReturnRes.error("帐号或者密码错误");
        }

    }
}
