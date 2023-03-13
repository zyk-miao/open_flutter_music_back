package com.zyk.music.utils;

import cn.hutool.core.util.StrUtil;
import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class MinioUtils {

    @Autowired
    private MinioClient minioClient;
    @Value("${minio.bucket}")
    private String bucket;
    @Value("http://${minio.url}:9000")
    private String url;

    public String putObject(InputStream inputStream, String objectName) throws Exception {
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucket)
                .object(objectName)
                .stream(inputStream, inputStream.available(), -1)
                .build());
        return getObjectUrlFromMinio(objectName);
    }

    public String putObject(byte[] bytes, String objectName) throws Exception {
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(objectName)
                        .stream(new ByteArrayInputStream(bytes), 0, -1)
                        .build());
        return getObjectUrlFromMinio(objectName);
    }

    public String getObjectUrlFromMinio(String objectName) throws Exception {
//        return minioClient.getPresignedObjectUrl(
//                GetPresignedObjectUrlArgs.builder()
//                        .method(Method.GET)
//                        .bucket(bucket)
//                        .object(objectName)
//                        .build());
        return url + "/" + bucket + "/" + objectName;
    }

    public String[] getObjectsNameList(String bucket) throws Exception {
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(bucket).build());
        List<String> list = new ArrayList();
        for (Result<Item> result : results) {
            list.add(result.get().objectName());
        }
        return list.toArray(new String[0]);

    }

    public void deleteObject(String objectName) throws Exception {
        minioClient.removeObject(
                RemoveObjectArgs.builder().bucket(bucket).object(objectName).build());
    }

    public void batchDeleteObject(List<String> objetsNameList) throws Exception {
        List<DeleteObject> objects = new LinkedList<>();
        for (String s : objetsNameList) {
            if (StrUtil.isNotEmpty(s)) {
                objects.add(new DeleteObject(s));
            }
        }
        Iterable<Result<DeleteError>> results = minioClient.removeObjects(
                RemoveObjectsArgs.builder().bucket(bucket).objects(objects).build());
        for (Result<DeleteError> result : results) {
            DeleteError error = result.get();
            System.out.println(
                    "Error in deleting object " + error.objectName() + "; " + error.message());
        }
    }

    public void deleteObjects(String bucket) throws Exception {
        List<DeleteObject> objects = new LinkedList<>();
        for (String s : getObjectsNameList(bucket)) {
            objects.add(new DeleteObject(s));
        }
        Iterable<Result<DeleteError>> results =
                minioClient.removeObjects(
                        RemoveObjectsArgs.builder().bucket(bucket).objects(objects).build());
        for (Result<DeleteError> result : results) {
            DeleteError error = result.get();
            System.out.println(
                    "Error in deleting object " + error.objectName() + "; " + error.message());
        }
    }

    public static void main(String[] args) throws Exception {


    }

}
