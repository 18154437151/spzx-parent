package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.date.DateTime;
import com.atguigu.spzx.manager.properties.MinioProperties;
import com.atguigu.spzx.manager.service.FileUploadService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Autowired
    private MinioProperties minioProperties;

    @Override
    public String upload(MultipartFile file) {
        try {
            // 1.建立和minio的连接
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(minioProperties.getEndpoint())
                            .credentials(minioProperties.getUser(), minioProperties.getPassword())
                            .build();
            // 2.判断bucket是否存在
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucket()).build());
            // 3.如果bucket不存在，创建
            if (!found) {
                // Make a new bucket called 'asiatrip'.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucket()).build());
            } else {
                System.out.println("Bucket 'spzx-sysuser-avatar' already exists.");
            }
            InputStream inputStream = file.getInputStream();  // 客户端选中的文件对应的输入流
            // 拼接一个日期目录字符串
            String dir = new DateTime().toString("yyyy/MM/dd");
            // 使用uuid（为了保证上传到minio服务器的文件名称唯一） + 客户端原文件名 拼接新文件名
            String originalFilename = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString().replaceAll("-","") + "_" + originalFilename;
            // 4.基于文件流上传
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(minioProperties.getBucket()).object(dir + "/" + fileName).stream(
                                    inputStream, inputStream.available(), -1)
                            .contentType("image/png")  // 上传的文件的类型
                            .build());
            // 5.返回url地址
            String url = minioProperties.getEndpoint() + "/" + minioProperties.getBucket() + "/" + dir + "/" + fileName;
            return url;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
