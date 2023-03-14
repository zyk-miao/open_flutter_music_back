package com.zyk.music.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zyk
 */
@Configuration
public class MinioConfig {

	@Value("http://${minio.url}")
    private String url;
    @Value("${minio.access}")
    private String accessKey;
    @Value("${minio.secret}")
    private String secretKey;

    @Bean
    public MinioClient getMinioClient() {
        MinioClient minioClient = MinioClient.builder().endpoint(url)
				.credentials(accessKey, secretKey).build();
        return minioClient;
    }

}
