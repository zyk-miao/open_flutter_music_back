package com.zyk.music.service;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.zyk.music.base.ReturnRes;
import com.zyk.music.entity.LoveMusic;
import com.zyk.music.entity.Music;
import com.zyk.music.entity.MusicTagRelationship;
import com.zyk.music.entity.vo.MusicBo;
import com.zyk.music.mapper.ComplexSelectMapper;
import com.zyk.music.mapper.LoveMusicMapper;
import com.zyk.music.mapper.MusicMapper;
import com.zyk.music.mapper.MusicTagRelationshipMapper;
import com.zyk.music.utils.MinioUtils;
import com.zyk.music.wrapper.LoveMusicQuery;
import com.zyk.music.wrapper.MusicQuery;
import com.zyk.music.wrapper.MusicTagRelationshipQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author zyk
 */
@Service
public class MusicServiceImpl {
    @Autowired
    MusicMapper musicMapper;
    @Autowired
    ComplexSelectMapper selectMapper;
    @Autowired
    MinioUtils minioUtils;
    @Autowired
    MusicTagRelationshipMapper musicTagRelationshipMapper;

    @Autowired
    LoveMusicMapper loveMusicMapper;

    @Value("${minio.musicPrefix}")
    String musicPrefix;


    public List<MusicBo> selectMusicList(String tagId, Music music, String userId) {
        List<MusicBo> musicList = selectMapper.getMusicListByTagId(tagId, music, userId);
        int index = 1;
        for (MusicBo m : musicList
        ) {
            m.setIndex(index++);

        }
        return musicList;
    }

    @Transactional(rollbackFor = Exception.class)
    public void delMusic(String musicId) throws Exception {
        Music music = musicMapper.findById(musicId);
        musicTagRelationshipMapper.delete(new MusicTagRelationshipQuery().where.musicId().eq(musicId).end());
        musicMapper.deleteById(musicId);
        loveMusicMapper.delete(new LoveMusicQuery().where.musicId().eq(musicId).end());
        minioUtils.deleteObject(music.getMinioFileName());
    }

    @Transactional(rollbackFor = Exception.class)
    public void delMusics(List<String> musicIds) throws Exception {
        List<Music> musicList = musicMapper.listByIds(musicIds);
        List<String> objectNameList = new ArrayList<>();
        for (Music music : musicList
        ) {
            objectNameList.add(music.getMinioFileName());
        }
        musicMapper.deleteByIds(musicIds);
        musicTagRelationshipMapper.delete(new MusicTagRelationshipQuery().where.musicId().in(musicIds).end());
        minioUtils.batchDeleteObject(objectNameList);
    }

    public ReturnRes<Object> addMusic(MultipartFile file, Music music) throws Exception {
        if (file == null || file.isEmpty()) {
            return ReturnRes.error("文件为空");
        }
        StringBuilder fileName = new StringBuilder(Objects.requireNonNull(file.getOriginalFilename()));
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        return addMusic(file.getInputStream(), music, ext);
    }

    public ReturnRes<Object> addMusic(InputStream inputStream, Music music, String ext) throws Exception {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        IoUtil.copy(inputStream,outputStream);
        inputStream.close();
        outputStream.toByteArray();
        ByteArrayInputStream in=new ByteArrayInputStream(outputStream.toByteArray());
        String md5 = org.springframework.util.DigestUtils.md5DigestAsHex(in);
        in.close();
        if (musicMapper.findOne(new MusicQuery().where.md5().eq(md5).end()) != null) {
            return ReturnRes.error("文件已存在");
        }
        if (StrUtil.isBlank(music.getMusicName())) {
            music.setMusicName("未知");
        }
        if (StrUtil.isBlank(music.getArtistName())) {
            music.setArtistName("未知");
        }
        music.setMd5(md5);
        music.setId(IdUtil.simpleUUID());
        music.setFileName(music.getMusicName() + "-" + music.getArtistName() +"."+ext);
        String fileName = music.getId() + "." + ext;
        music.setMinioFileName(musicPrefix+fileName);
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(outputStream.toByteArray());
        String objectUrl = minioUtils.putObject(byteArrayInputStream, musicPrefix + fileName);
        byteArrayInputStream.close();
        outputStream.close();
        music.setMusicUrl(objectUrl);
//        music.setCreateTime(new Date());
        musicMapper.insertWithPk(music);
        return ReturnRes.success("添加成功");
    }

    public int addTo(List<String> musicIds, String tagId) {
        List<MusicTagRelationship> list = new ArrayList<>();
//        List<String> existMusicIds = musicTagRelationshipMapper.
//                listObjs(new MusicTagRelationshipQuery().select.musicId().end().where.musicId().
//                        in(musicIds).tagId().eq(tagId).end());
//        musicIds.removeAll(existMusicIds);
        for (String musicId : musicIds
        ) {
            list.add(new MusicTagRelationship(musicId, tagId, null, IdUtil.simpleUUID()));
        }
//        return musicTagRelationshipMapper.save(list);
        if (ObjectUtils.isEmpty(list)) {
            return 0;
        }
        return musicTagRelationshipMapper.insertBatchWithPk(list);
    }

    public int editMusic(Music music) {
        Music m=musicMapper.findById(music.getId());
        String ext= FileNameUtil.getSuffix(m.getFileName());
        music.setFileName(StrUtil.format("{}-{}.{}",music.getMusicName(),music.getArtistName(),ext));
        return musicMapper.updateById(music);
    }

    public int removeMusics(List<String> musicIds, String tagId) {
        return musicTagRelationshipMapper.delete(new MusicTagRelationshipQuery()
                .where.musicId().in(musicIds)
                .tagId().eq(tagId).end());
    }

    public ReturnRes<Object> addTo(List<String> musicIds, List<String> tagIds) {
        List<MusicTagRelationship> list = new ArrayList<>();
        for (String mid : musicIds) {
            for (String tId : tagIds
            ) {
                list.add(new MusicTagRelationship(mid, tId, null, IdUtil.simpleUUID()));
            }
        }
        List<MusicTagRelationship> existList = musicTagRelationshipMapper
                .listEntity(new MusicTagRelationshipQuery().where.tagId().in(tagIds).end());
        list.removeAll(existList);
        if (!list.isEmpty()) {
            musicTagRelationshipMapper.insertBatchWithPk(list);
        }
        return ReturnRes.success("添加成功");
    }

    public int addLoveMusic(List<String> musicIds, String userId) {
        List<LoveMusic> list = new ArrayList<>();
        for (String m : musicIds) {
            list.add(new LoveMusic(IdUtil.simpleUUID(), userId, m, new Date()));
        }
        return loveMusicMapper.insertBatchWithPk(list);
    }


    public List<MusicBo> selectLoveList(String userId) {
        List<MusicBo> musicList = selectMapper.selectLoveList(userId);
        int index = 1;
        for (MusicBo m : musicList
        ) {
            m.setIndex(index++);

        }
        return musicList;
    }

    public int cancelLoveMusic(String musicId, String userId) {
        return loveMusicMapper.delete(new LoveMusicQuery().where.musicId().eq(musicId).userId().eq(userId).end());
    }

}
