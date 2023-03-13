package com.zyk.music.runner;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author ZYK
 */
@Component
public class StartTask implements ApplicationRunner {
    @Autowired
    MinioClient minioClient;
    @Value("${minio.bucket}")
    private String bucket;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        boolean found =
                minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        if (found) {
            System.out.println("桶已存在,跳过创建");
        } else {
            System.out.println("桶不存在,创建");
            minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(bucket)
                            .build());
        }
    }
}