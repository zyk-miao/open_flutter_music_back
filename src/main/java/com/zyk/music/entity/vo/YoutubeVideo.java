package com.zyk.music.entity.vo;

import lombok.Data;

@Data
public class YoutubeVideo {
    private String title;
    private String videoId;

    private int lengthSeconds;
}
