package com.zyk.music.service;

import cn.hutool.core.util.IdUtil;
import com.zyk.music.base.ReturnRes;
import com.zyk.music.entity.Music;
import com.zyk.music.entity.Tag;
import com.zyk.music.entity.vo.TagBo;
import com.zyk.music.mapper.ComplexSelectMapper;
import com.zyk.music.mapper.MusicTagRelationshipMapper;
import com.zyk.music.mapper.TagMapper;
import com.zyk.music.utils.MinioUtils;
import com.zyk.music.wrapper.MusicTagRelationshipQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zyk
 */
@Service
public class TagServiceImpl {
    @Autowired
    TagMapper tagMapper;

    @Autowired
    MinioUtils minioUtils;

    @Autowired
    MusicTagRelationshipMapper musicTagRelationshipMapper;


    @Autowired
    ComplexSelectMapper selectMapper;

    @Value("${minio.imgPrefix}")
    String imgPrefix;

    public List<TagBo> selectTagList(String userId) {
        return selectMapper.selectTagListByUser(userId);
    }

    @Transactional
    public ReturnRes<?> delTag(String tagId) {
        musicTagRelationshipMapper.delete(new MusicTagRelationshipQuery().where.tagId().eq(tagId).end());
        tagMapper.deleteById(tagId);
        return ReturnRes.success("删除成功");
    }

    public ReturnRes<?> putTag(MultipartFile file, Tag tag) throws Exception {
        if (file != null && !file.isEmpty()) {
            StringBuilder fileName = new StringBuilder(Objects.requireNonNull(file.getOriginalFilename()));
            fileName.replace(0, fileName.lastIndexOf("."), tag.getId());
            String objectUrl = minioUtils.putObject(file.getInputStream(), imgPrefix + fileName);
            tag.setCoverImg(objectUrl);
            Tag t = tagMapper.findById(tag.getId());
            minioUtils.deleteObject(t.getCoverImg());
        }
        tagMapper.updateById(tag);
        System.out.println(tag);
        return ReturnRes.success("更新成功", tag);
    }

    public ReturnRes<Object> addTag(MultipartFile file, Tag tag, String userId) throws Exception {
        tag.setId(IdUtil.simpleUUID());
        tag.setUserId(userId);
        if (file != null && !file.isEmpty()) {
            StringBuilder fileName = new StringBuilder(Objects.requireNonNull(file.getOriginalFilename()));
            fileName.replace(0, fileName.lastIndexOf("."), tag.getId());
            String objectUrl = minioUtils.putObject(file.getInputStream(), imgPrefix + fileName);
            tag.setCoverImg(objectUrl);
        }
        tagMapper.insertWithPk(tag);
        return ReturnRes.success("添加成功");
    }

    public void delTags(List<String> tagIds) throws Exception {
        List<Tag> tags = tagMapper.listByIds(tagIds);
        List<String> list = tags.stream().map(Tag::getCoverImg).collect(Collectors.toList());
        int i = tagMapper.deleteByIds(tagIds);
        if (i == tagIds.size()) {
            minioUtils.batchDeleteObject(list);
        }
    }

    public List<TagBo> selectTagList(String userId, Music music) {
        return selectMapper.selectTagList(userId, music);

    }

}
