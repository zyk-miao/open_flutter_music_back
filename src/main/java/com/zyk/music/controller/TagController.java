package com.zyk.music.controller;

import com.zyk.music.base.ReturnRes;
import com.zyk.music.entity.Music;
import com.zyk.music.entity.Tag;
import com.zyk.music.entity.vo.TagBo;
import com.zyk.music.service.TagServiceImpl;
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
public class TagController {
    @Autowired
    TagServiceImpl tagService;

    @PostMapping("/selectTagList")
    public ReturnRes<List<TagBo>> selectTagList(@RequestHeader String token) {
        String userId=TokenUtil.getUserIdFromToken(token);
        List<TagBo> tagList = tagService.selectTagList(userId);
        return ReturnRes.success("查询成功", tagList);
    }

    @PostMapping("/delTag")
    public ReturnRes<Object> delTag(String tagId) {
        tagService.delTag(tagId);
        return ReturnRes.success("删除成功");
    }

    @PostMapping("/putTag")
    public ReturnRes<?> delTag(@RequestBody MultipartFile file, Tag tag) throws Exception {
        return tagService.putTag(file,tag);
    }

    @PostMapping("/addTag")
    public ReturnRes<Object> addTag(@RequestBody MultipartFile file, Tag tag,@RequestHeader String token) throws Exception {
        String userId=TokenUtil.getUserIdFromToken(token);
        return tagService.addTag(file,tag,userId);
    }

    @PostMapping("/delTags")
    public ReturnRes<Object> delTags(@RequestBody List<String> tagIds) throws Exception {
        tagService.delTags(tagIds);
        return ReturnRes.success("删除成功");
    }

    @GetMapping("/selectTagListWithFlag")
    public ReturnRes<List<TagBo>> selectTagList(Music music,@RequestHeader String token) {
        String userId=TokenUtil.getUserIdFromToken(token);
        List<TagBo> tagList = tagService.selectTagList(userId,music);
        return ReturnRes.success("查询成功", tagList);
    }
}
