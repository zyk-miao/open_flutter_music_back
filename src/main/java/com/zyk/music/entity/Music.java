package com.zyk.music.entity;

import cn.org.atool.fluent.mybatis.annotation.FluentMybatis;
import cn.org.atool.fluent.mybatis.annotation.TableField;
import cn.org.atool.fluent.mybatis.annotation.TableId;
import cn.org.atool.fluent.mybatis.base.RichEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (Music)实体类
 *
 * @author makejava
 * @since 2022-01-25 16:17:07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@FluentMybatis
@AllArgsConstructor
@NoArgsConstructor
public class Music extends RichEntity implements Serializable {
    private static final long serialVersionUID = 138708857312359457L;

    @TableId
    private String id;
    
    private String musicName;
    
    private String artistName;
    
    private String musicUrl;
    
    private String fileName;
    @TableField(insert = "CURRENT_TIMESTAMP(6)")
    private Date createTime;

    private String minioFileName;

    private String md5;


}

