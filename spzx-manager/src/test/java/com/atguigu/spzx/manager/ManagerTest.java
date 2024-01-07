package com.atguigu.spzx.manager;

import com.alibaba.excel.EasyExcel;
import com.atguigu.spzx.manager.mapper.SysCategoryMapper;
import com.atguigu.spzx.manager.mapper.SysRoleMapper;
import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.*;
import com.atguigu.spzx.manager.service.impl.SysCategoryServiceImpl;
import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import io.minio.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

// 单元测试类所在的包，一定和启动类所在的包写成一样！！
@SpringBootTest
public class ManagerTest {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysCategoryService sysCategoryService;

    @Autowired
    private SysCategoryMapper sysCategoryMapper;

    @Autowired
    private CategoryBrandService categoryBrandService;
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
    @Test
    public void test5(){
        List<Long> roleIdList = Arrays.asList(1L, 5L, 9L);
        Long userId = 100L;
        AssginRoleDto assginRoleDto = new AssginRoleDto();
        assginRoleDto.setUserId(userId);
        assginRoleDto.setRoleIdList(roleIdList);
        sysRoleService.doAssign(assginRoleDto);
    }
    @Test
    public void test6(){
        List<SysMenuVo> voList = sysMenuService.menus(6L,0L);
        System.out.println(voList);
    }
    @Test
    public void test7(){
        List<Category> byParentId = sysCategoryService.findByParentId(0L);
        byParentId.forEach(category -> {
            System.out.println(category);
        });
    }

    // 创建exel文档，一个sheet表，三行数据
    @Test
    public void test8(){
        StudentExcelVo studentExcelVo1 = new StudentExcelVo(101,"张三",20);
        StudentExcelVo studentExcelVo2 = new StudentExcelVo(102,"李四",20);
        StudentExcelVo studentExcelVo3 = new StudentExcelVo(103,"王五",50);
        List<StudentExcelVo> data = Arrays.asList(studentExcelVo1,studentExcelVo2,studentExcelVo3);
        EasyExcel.write("C:\\Users\\Administrator\\Desktop\\学生列表.xslx",StudentExcelVo.class)
                .sheet("一班学生").doWrite(data);
    }

    @Test
    public void test9(){
        // 读取C:\Users\Administrator\Desktop\学生列表.xslx中的数据
        EasyExcel.read("C:\\Users\\Administrator\\Desktop\\学生列表.xslx",StudentExcelVo.class,new StudentReadListener())
                .sheet("一班学生")  // 指定你要读取哪一个sheet表
                .doRead();
    }

    @Test
    public void test10(){
//        Map idsByThreeId = sysCategoryMapper.getIdsByThreeId(162L);
//        System.out.println(idsByThreeId);
        List<Long> idList = categoryBrandService.getIdList(162L);
        System.out.println(idList);

    }
}
