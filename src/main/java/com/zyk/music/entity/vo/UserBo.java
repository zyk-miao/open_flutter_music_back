package com.zyk.music.entity.vo;


import com.zyk.music.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class UserBo extends User {
    private String token;
}
