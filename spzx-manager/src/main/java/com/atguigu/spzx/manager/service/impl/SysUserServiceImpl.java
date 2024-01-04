package com.atguigu.spzx.manager.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.exp.SpzxGuiguException;
import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public LoginVo login(LoginDto loginDto) {
        // 0.校验验证码
        String captcha = loginDto.getCaptcha();  // 用户输入的验证码
        String codeKey = loginDto.getCodeKey();
        String redisCode = redisTemplate.opsForValue().get(codeKey);
        // 判断redis中验证码是否存在
        if (StringUtils.isEmpty(redisCode)){
            throw new SpzxGuiguException(ResultCodeEnum.VALIDATECODE_NOT_EXIST);  // 验证码已过期
        }
        if (!redisCode.equals(captcha)){
            throw new SpzxGuiguException(ResultCodeEnum.VALIDATECODE_ERROR);  // 验证码错误
        }
        // 验证码校验通过，从Redis中删除
        redisTemplate.delete(codeKey);

        // 1.非空校验
        String userName = loginDto.getUserName();
        String password = loginDto.getPassword();  // 用户输入的明文密码
        if(StringUtils.isEmpty(userName)){
            // 要求：抛出自定义的异常
            // throw new RuntimeException("用户名为空");
            throw new SpzxGuiguException(ResultCodeEnum.USERNAME_EMPTY);
        }
        if (StringUtils.isEmpty(password)){
            throw new SpzxGuiguException(ResultCodeEnum.PASSWORD_EMPTY);
            // throw new RuntimeException("密码为空");
        }
        // 2.从数据库中根据用户名查询当前用户是否存在，如果不存在也要抛异常
        SysUser sysUser = sysUserMapper.selectByUsername(userName);
        if (sysUser == null){
            throw new SpzxGuiguException(ResultCodeEnum.USER_NOT_EXITS);
            // throw new RuntimeException("该用户不存在");
        }
        // 3.用户存在，校验密码
        String password1 = sysUser.getPassword();  // 数据库中加密的密码
        if (!DigestUtil.md5Hex(password).equals(password1)){
            throw new SpzxGuiguException(ResultCodeEnum.LOGIN_ERROR);
            // throw new RuntimeException("密码不正确");
        }
        // 4.附加：校验用户的状态是否被锁定
        if (sysUser.getStatus() == 0){
            throw new SpzxGuiguException(ResultCodeEnum.ACCOUNT_STOP);
            // throw new RuntimeException("该用户已被禁用");
        }
        // 5.使用uuid生成一个唯一字符串，作为token
        String token = UUID.randomUUID().toString().replaceAll("-", "");// 去掉所有的横线-
        // 6.将当前用户转为json字符串，作为redis中的value
        String value = JSON.toJSONString(sysUser); // alibaba fastjson
        // 7.存储到redis中，过期时间为30分钟
        redisTemplate.opsForValue().set("user:login:" + token,value,30, TimeUnit.MINUTES);
        // 8.将token封装成LoginVo对象，返回即可
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        return loginVo;
    }

    @Override
    public Map getUserInfo(String token) {
        // 从redis中查询
        String key = "user:login:" + token;
        String jsonString = redisTemplate.opsForValue().get(key);
        // JSON.toJSONString(xxx)  java对象转成josn字符串
        // 将json字符串转成SysUser类型的对象
        SysUser sysUser = JSON.parseObject(jsonString, SysUser.class);
        String userName = sysUser.getUserName();
        String avatar = sysUser.getAvatar();
        HashMap hashMap = new HashMap();
        hashMap.put("name",userName);
        hashMap.put("avatar",avatar);
        return hashMap;
    }

    @Override
    public void logout(String token) {
        String key = "user:login:" + token;
        redisTemplate.delete(key);
    }

    @Override
    public PageInfo findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto) {
        // mybatis的条件分页查询，固定的写法：
        // 1.设置分页参数 (SQL中自动添加limit ?,?)
        PageHelper.startPage(pageNum,pageSize);
        // 2.执行条件SQL 返回list结果集 select * from sys_user where (username like '%1111%' or name like '%1111%' or phone like '%1111%' and create_time >= '2022-09-11' and create_time <= '2023-01-01')
        List<SysUser> list = sysUserMapper.findList(sysUserDto);
        // 3.将list结果集封装成pageInfo对象
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public void addSysUser(SysUser sysUser) {
        // 1.进行非空校验
        String userName = sysUser.getUserName();
        String password = sysUser.getPassword();
        String name = sysUser.getName();
        String phone = sysUser.getPhone();
        if (StringUtils.isEmpty(userName)){
            throw new SpzxGuiguException(201,"用户名不能为空");
        }
        if (StringUtils.isEmpty(password)){
            throw new SpzxGuiguException(201,"密码不能为空");
        }
        if (StringUtils.isEmpty(name)){
            throw new SpzxGuiguException(201,"真实姓名不能为空");
        }
        if (StringUtils.isEmpty(phone)){
            throw new SpzxGuiguException(201,"手机号不能为空");
        }
        // 2.校验username是否重复
        SysUser sysUserFromDB = sysUserMapper.selectByUsername(userName);  // 之前在登录时实现的方法（根据用户名查询该用户是否存在）
        if (sysUserFromDB != null){
            // 该用户已经存在
            throw new SpzxGuiguException(202,"用户名被占用");
        }
        // 3.对密码加密存储
        String md5Password = DigestUtil.md5Hex(password);
        sysUser.setPassword(md5Password);
        sysUser.setStatus(1);  // 默认正常状态
        // 4.附加的校验，手机号不允许重复
        Integer count = sysUserMapper.selectByPhone(phone);
        if (count > 0){
            throw new SpzxGuiguException(202,"改手机号被占用");
        }
        // 5.执行insert
        sysUserMapper.addSysUser(sysUser);
    }

    @Override
    public void updateSysUser(SysUser sysUser) {
        // 可以修改哪些字段？
        String userName = sysUser.getUserName();
        String name = sysUser.getName();
        String phone = sysUser.getPhone();
        String avatar = sysUser.getAvatar();
        String description = sysUser.getDescription();
        Long id = sysUser.getId();  // 当前用户id
        // 非空校验（略）
        // userName和phone同样需要保证唯一性（和修改角色时，校验方式一样的）
        // 根据用户id查询到当前用户，如果数据库中的用户名和密码，和前端传递的用户名和密码不同，就需要对传递过来的数据进行重复性的校验
        SysUser sysUserFromDB = sysUserMapper.selectById(id);
        if (!sysUserFromDB.getUserName().equals(userName)){
            // 对userName（前端传递的） 进行重复性校验
            if (sysUserMapper.selectByUsername(userName)!= null){
                // 该用户名已经存在
                throw new SpzxGuiguException(201,"该用户名已被占用");
            }
        }
        if (!sysUserFromDB.getPhone().equals(phone)){
            // 对phone（前端传递的） 进行重复性校验
            if (sysUserMapper.selectByPhone(phone) > 0){
                // 该手机号已被占用
                throw new SpzxGuiguException(201,"该手机号已被占用");
            }
        }
        sysUserMapper.updateById(sysUser);
    }

    @Override
    public void deleteSysUser(Long id) {
        sysUserMapper.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        for (Long id : ids) {
            this.deleteSysUser(id);
        }
    }
}
