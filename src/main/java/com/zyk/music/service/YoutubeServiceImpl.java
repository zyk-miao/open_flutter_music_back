package com.zyk.music.service;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.request.RequestSearchResult;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.request.RequestVideoStreamDownload;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.search.SearchResult;
import com.github.kiulian.downloader.model.search.SearchResultItem;
import com.github.kiulian.downloader.model.search.SearchResultVideoDetails;
import com.github.kiulian.downloader.model.search.field.TypeField;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.Format;
import com.zyk.music.base.ReturnRes;
import com.zyk.music.entity.Music;
import com.zyk.music.entity.vo.YoutubeVideo;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.github.kiulian.downloader.model.search.SearchResultItemType.VIDEO;

@Service
public class YoutubeServiceImpl {

    @Autowired
    MusicServiceImpl musicService;
    @Autowired
    YoutubeDownloader downloader;
    static final Map<String, byte[]> audios = ExpiringMap.builder()
            .expiration(10, TimeUnit.MINUTES)
            .build();

    private static final String YOUTUBE_AUDIO_EXT = "m4a";

    public ReturnRes<Object> addMusicFromYoutube(Music music, String videoId) throws Exception {
        byte[] bytes = getYoutubeBytesByVideoId(videoId);
        return musicService.addMusic(new ByteArrayInputStream(bytes), music, YOUTUBE_AUDIO_EXT);
    }

    public ResponseEntity<ByteArrayResource> playMusicFromYoutube(String youtubeVideoId) throws IOException {
        byte[] audioBytes = getYoutubeBytesByVideoId(youtubeVideoId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "audio/mp3");
        return ResponseEntity.ok().headers(headers).body(new ByteArrayResource(audioBytes));
    }

    public byte[] getBytesFromYoutubeVideoId(String videoId) throws IOException {
        System.out.println("正在下载视频 Id为:" + videoId);
        RequestVideoInfo req = new RequestVideoInfo(videoId);
        Response<VideoInfo> resp = downloader.getVideoInfo(req);
        VideoInfo video = resp.data();
        Format format = video.bestAudioFormat();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        RequestVideoStreamDownload r = new RequestVideoStreamDownload(format, os);
        Response<Void> res = downloader.downloadVideoStream(r);
        byte[] bytes = os.toByteArray();
        os.close();
        audios.put(videoId, bytes);
        return bytes;
    }

    public byte[] getYoutubeBytesByVideoId(String videoId) throws IOException {
        return audios.containsKey(videoId) ? audios.get(videoId) : getBytesFromYoutubeVideoId(videoId);
    }

    public ReturnRes<Object> searchVideoFromYoutube(String key) {
        RequestSearchResult request = new RequestSearchResult(key)
                .type(TypeField.VIDEO).forceExactQuery(true);
        SearchResult result = downloader.search(request).data();
        List<SearchResultItem> items = result.items();
        List<YoutubeVideo> list = items.stream().filter((item)-> item.type().equals(VIDEO)).map((item) -> {
                YoutubeVideo youtubeVideo = new YoutubeVideo();
            SearchResultVideoDetails asVideo = item.asVideo();
            BeanUtils.copyProperties(asVideo, youtubeVideo);
            return youtubeVideo;
        }).collect(Collectors.toList());
        return ReturnRes.success("请求成功", list);
    }
}
