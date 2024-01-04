package com.atguigu.spzx.manager;

import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import io.minio.*;
import io.minio.errors.MinioException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
// 单元测试类所在的包，一定和启动类所在的包写成一样！！
@SpringBootTest
public class ManagerTest {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Test
    public void test1(){
        redisTemplate.opsForValue().set("username","尚品甄选");
    }

    @Test
    public void test2(){
        List<SysUser> all = sysUserMapper.findAll();
        all.forEach(sysUser -> {
            System.out.println(sysUser);
        });

    }
    @Test
    public void test3(){
        SysUserDto sysUserDto = new SysUserDto();
        sysUserDto.setKeyword("1111");
        sysUserDto.setCreateTimeBegin("2023-11-11");
        sysUserDto.setCreateTimeEnd("2024-12-12");
        sysUserService.findByPage(1,3,sysUserDto);
    }
    @Test
    public void test4(){
        try {
            // Create a minioClient with the MinIO server playground, its access key and secret key.
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint("https://192.168.119.137:9000")
                            .credentials("admin", "admin123456")
                            .build();

            // Make 'asiatrip' bucket if not exist.
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket("spzx-sysuser-avatar").build());
            if (!found) {
                // Make a new bucket called 'asiatrip'.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("spzx-sysuser-avatar").build());
            } else {
                System.out.println("Bucket 'spzx-sysuser-avatar' already exists.");
            }

            // Upload '/home/user/Photos/asiaphotos.zip' as object name 'asiaphotos-2015.zip' to bucket
            // 'asiatrip'.
//            minioClient.uploadObject(
//                    UploadObjectArgs.builder()
//                            .bucket("asiatrip")
//                            .object("asiaphotos-2015.zip")
//                            .filename("/home/user/Photos/asiaphotos.zip")
//                            .build());
            // Upload known sized input stream.
            // 构建一个文件输入流
            InputStream inputStream = new FileInputStream("C:\\Users\\Administrator\\Desktop\\20240101212137.png");
            // 基于文件流的方式进行文件上传
            minioClient.putObject(
                    PutObjectArgs.builder().bucket("spzx-sysuser-avatar").object("2024/01/02/xxx.png").stream(
                                    inputStream, inputStream.available(), -1)
                            .contentType("image/png")  // 上传的文件的类型
                            .build());
            System.out.println(
                    "'/home/user/Photos/asiaphotos.zip' is successfully uploaded as "
                            + "object 'asiaphotos-2015.zip' to bucket 'asiatrip'.");
        } catch (Exception e) {
            System.out.println("Error occurred: " + e);
        }
    }
}
