package com.zyk.music;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.request.RequestSearchResult;
import com.github.kiulian.downloader.model.search.SearchResultItem;
import com.github.kiulian.downloader.model.search.SearchResultVideoDetails;
import com.github.kiulian.downloader.model.search.field.TypeField;
import com.zyk.music.entity.vo.YoutubeVideo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.kiulian.downloader.model.search.SearchResultItemType.VIDEO;

public class Main {


    public static void main(String[] args) {
        YoutubeDownloader downloader = new YoutubeDownloader();
        RequestSearchResult request = new RequestSearchResult("烟花易冷")
                .type(TypeField.VIDEO).forceExactQuery(true);
        List<SearchResultItem> items = downloader.search(request).data().items();
        List<YoutubeVideo> list = items.stream().filter((item)-> item.type().equals(VIDEO)).map((item) -> {
                YoutubeVideo youtubeVideo = new YoutubeVideo();
            SearchResultVideoDetails asVideo = item.asVideo();
            BeanUtils.copyProperties(asVideo, youtubeVideo);
            return youtubeVideo;
        }).collect(Collectors.toList());
        System.out.println(list.toString());
    }
}


