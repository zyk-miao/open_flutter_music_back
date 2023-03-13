package com.zyk.music.controller;

import cn.hutool.core.util.StrUtil;
import com.zyk.music.base.ReturnRes;
import com.zyk.music.entity.Music;
import com.zyk.music.entity.vo.MusicBo;
import com.zyk.music.entity.vo.MusicListWithTagId;
import com.zyk.music.service.MusicServiceImpl;
import com.zyk.music.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author zyk
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class MusicController {
    @Autowired
    MusicServiceImpl musicService;

    @PostMapping("/selectMusicList")
    public ReturnRes<MusicListWithTagId> selectTagList(String tagId, Music music, @RequestHeader String token) {
        String userId=TokenUtil.getUserIdFromToken(token);
        List<MusicBo> musicList = musicService.selectMusicList(tagId, music,userId);
        if(StrUtil.isEmpty(tagId)){
            tagId="all";
        }
        MusicListWithTagId musicListWithTagId =new MusicListWithTagId(tagId,musicList);
        return ReturnRes.success("查询成功", musicListWithTagId);
    }

    @PostMapping("/delMusic")
    public ReturnRes<Object> delMusic(String musicId,@RequestHeader String token) throws Exception {
        String userIdFromToken = TokenUtil.getUserIdFromToken(token);
        if(!"0".equals(userIdFromToken)){
            return ReturnRes.error("没有权限");
        }
        musicService.delMusic(musicId);
        return ReturnRes.success("删除成功");
    }

    @PostMapping("/delMusics")
    public ReturnRes<Object> delMusics(@RequestBody List<String> musicIds) throws Exception {
        musicService.delMusics(musicIds);
        return ReturnRes.success("删除成功");
    }

    @PostMapping("/addMusic")
    public ReturnRes<Object> addMusic(@RequestBody MultipartFile file,Music param) throws Exception {
        return musicService.addMusic(file, param);
    }

    @PostMapping("/batchAddMusicTo")
    public ReturnRes<Object> addTO(@RequestBody List<String> musicIds, @RequestParam String tagId) throws Exception {
        musicService.addTo(musicIds, tagId);
        return ReturnRes.success("添加成功");
    }

    @PostMapping("/addMusicsToTags")
    public ReturnRes<Object> addTo(@RequestBody List<String> musicIds,@RequestParam List<String> tagIds){
        return musicService.addTo(musicIds, tagIds);
    }
    @PostMapping("/editMusic")
    public ReturnRes<Object> editMusic(@RequestBody Music music) throws Exception {
        if(musicService.editMusic(music)>0){
            return ReturnRes.success("修改成功");
        }
        else{
            return ReturnRes.error("修改失败");
        }
    }

    @PostMapping("/removeMusics")
    public ReturnRes<Object> removeMusic(@RequestBody List<String> musicIds, String tagId) {
        musicService.removeMusics(musicIds, tagId);
        return ReturnRes.success("移除成功");
    }

    @PostMapping("/addLoveMusic")
    public ReturnRes<Object> addLoveMusic(@RequestBody List<String> musicIds,@RequestHeader String token){
        String userId= TokenUtil.getUserIdFromToken(token);
        musicService.addLoveMusic(musicIds,userId);
        return ReturnRes.success("收藏成功");
    }
    @DeleteMapping("/cancelLoveMusic")
    public ReturnRes<Object> cancelLoveMusic(String musicId,@RequestHeader String token){
        String userId= TokenUtil.getUserIdFromToken(token);
        musicService.cancelLoveMusic(musicId,userId);
        return ReturnRes.success("取消收藏成功");
    }

    @GetMapping("/selectLoveList")
    public ReturnRes<MusicListWithTagId> selectLoveList(@RequestHeader String token){
        String userId= TokenUtil.getUserIdFromToken(token);
        MusicListWithTagId musicListWithTagId =new MusicListWithTagId("love",musicService.selectLoveList(userId));
        return ReturnRes.success("查询成功", musicListWithTagId);
    }
}
