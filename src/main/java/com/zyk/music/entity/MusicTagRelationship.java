package com.zyk.music.entity;

import cn.org.atool.fluent.mybatis.annotation.FluentMybatis;
import cn.org.atool.fluent.mybatis.annotation.TableField;
import cn.org.atool.fluent.mybatis.annotation.TableId;
import cn.org.atool.fluent.mybatis.base.IEntity;
import cn.org.atool.fluent.mybatis.base.RichEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (MusicTagRelationship)实体类
 *
 * @author makejava
 * @since 2022-02-11 23:54:05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@FluentMybatis
@AllArgsConstructor
@NoArgsConstructor
public class MusicTagRelationship extends RichEntity implements Serializable {
    private static final long serialVersionUID = -63051230072251343L;


    private String musicId;

    private String tagId;

    @TableField(insert = "CURRENT_TIMESTAMP(6)")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @EqualsAndHashCode.Exclude
    private Date createTime;

    @TableId
    @EqualsAndHashCode.Exclude
    private String id;


}

