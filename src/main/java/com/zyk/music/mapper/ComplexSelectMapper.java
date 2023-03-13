package com.zyk.music.mapper;

import com.zyk.music.entity.Music;
import com.zyk.music.entity.vo.MusicBo;
import com.zyk.music.entity.vo.TagBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zyk
 */
@Mapper
public interface ComplexSelectMapper {
//    @MapKey("id")
    List<MusicBo> getMusicListByTagId(@Param("tagId") String tagId, @Param("music") Music music,@Param("userId") String userId);

    List<TagBo> selectTagList(@Param("userId") String userId,@Param("music") Music music);
    List<TagBo> selectTagListByUser(@Param("userId") String userId);

    List<MusicBo> selectLoveList(@Param("userId") String userId);

}
