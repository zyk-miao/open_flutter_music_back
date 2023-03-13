package com.zyk.music.config;

import com.github.kiulian.downloader.YoutubeDownloader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YoutubeDownloaderConfig {

    @Bean
    public YoutubeDownloader youtubeDownloader(){
        return new YoutubeDownloader();
    }
}
