package com.zyk.music.utils;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.zyk.music.exception.TokenUnavailableException;

public class TokenUtil {
    public static void verify(String token) {
        final JWT jwt = JWTUtil.parseToken(token);
        String userId = String.valueOf(jwt.getPayload("userId"));
        boolean b = JWTUtil.verify(token, userId.getBytes());
        if (!b) {
            throw new TokenUnavailableException();
        }
    }
    public static String getUserIdFromToken(String token){
        final JWT jwt = JWTUtil.parseToken(token);
        return String.valueOf(jwt.getPayload("userId"));
    }
}
