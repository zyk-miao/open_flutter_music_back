package com.zyk.music.entity;

import cn.org.atool.fluent.mybatis.annotation.FluentMybatis;
import cn.org.atool.fluent.mybatis.annotation.TableId;
import cn.org.atool.fluent.mybatis.base.IEntity;
import cn.org.atool.fluent.mybatis.base.RichEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.io.Serializable;
@Data
@EqualsAndHashCode(callSuper = false)
@FluentMybatis
@AllArgsConstructor
@NoArgsConstructor
public class User extends RichEntity implements Serializable {
    @TableId
    private String id;

    private String username;

    private String password;
}
