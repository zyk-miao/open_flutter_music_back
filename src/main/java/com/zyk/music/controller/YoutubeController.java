package com.zyk.music.controller;


import com.zyk.music.base.ReturnRes;
import com.zyk.music.entity.Music;
import com.zyk.music.service.YoutubeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class YoutubeController {

    @Autowired
    YoutubeServiceImpl youtubeService;

    //    ThreadLocal<Map<String,byte[]>>
    @PostMapping("/addMusicFromYoutube")
    public ReturnRes<Object> addMusicFromYoutube(@RequestBody Music music, String videoId) throws Exception {
        return youtubeService.addMusicFromYoutube(music, videoId);
    }

    @GetMapping("/playMusicFromYoutube")
    public ResponseEntity<ByteArrayResource> playMusicFromYoutube(String youtubeVideoId) throws IOException {
        return youtubeService.playMusicFromYoutube(youtubeVideoId);
    }

    @GetMapping("/searchVideoFromYoutube")
    public ReturnRes<Object> searchVideoFromYoutube(String key){
        return youtubeService.searchVideoFromYoutube(key);
    }
}
