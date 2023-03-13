package com.zyk.music.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MusicListWithTagId {
    String tagId;
    List<MusicBo> dataList;
}
