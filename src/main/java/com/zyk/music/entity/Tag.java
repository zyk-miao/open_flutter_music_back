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
 * (Tag)实体类
 *
 * @author makejava
 * @since 2022-01-25 16:16:02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@FluentMybatis
@AllArgsConstructor
@NoArgsConstructor
public class Tag extends RichEntity implements Serializable {
    private static final long serialVersionUID = 356621738564016961L;
    @TableId
    private String id;
    
    private String tagName;

//    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    @TableField(insert = "CURRENT_TIMESTAMP(6)")
    private Date createTime;

    private String userId;

    private String coverImg;

}

