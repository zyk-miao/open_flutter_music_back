package com.zyk.music.entity.vo;

import com.zyk.music.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagBo extends Tag {
        boolean flag;
        int num;
}
