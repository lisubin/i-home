package com.lsb116.ihome.api;

import com.aliyuncs.exceptions.ClientException;
import com.lsb116.ihome.entity.Member;
import com.lsb116.ihome.mapper.MemberMapper;
import com.lsb116.ihome.service.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.rmi.server.UID;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/api")
public class MemberApi {

    @Autowired
    SMSService smsService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired(required = false)
    MemberMapper memberMapper;



    @PostMapping("/register")
    @ResponseBody
    @CrossOrigin("*")
    public Map<String,Object> register(String mobile,String password,String formCode,String token){

        Map<String, Object> r = new HashMap<>();
        String phoneCode = redisTemplate.opsForValue().get(token+mobile);
        if(phoneCode==null || !phoneCode.equals(formCode)){
            r.put("msg", "校验码过期或输入错误");
            r.put("code", 1);
        }else{
            Member member = new Member();
            member.setMobile(mobile);
            member.setPassword(password);
            memberMapper.add(member);
            r.put("msg", "注册成功");
            r.put("code", 0);
        }
        return r;
    }







    @GetMapping("/registerCode")
    @ResponseBody //返回给客户端的是数据 不再是视图
    @CrossOrigin("*") // 允许所有的域名跨域访问该资源
    public Map<String, Object> registerCode(String mobile) throws ClientException {
       // String phoneCode = smsService.getCode(mobile);

        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        //生成一个[0,9]整数
        for (int i = 0; i < 5; i++) {
            //System.out.println( r.nextInt(10));
            buffer.append(random.nextInt(10));
        }

        String phoneCode = buffer.toString();
        System.out.println(phoneCode);
        String token = UUID.randomUUID().toString();

        // 将token作为ken 将phoneCode作为value 放入redis，设置过期时间是60s
        redisTemplate.opsForValue().set(token + mobile, phoneCode);
        redisTemplate.expire(token + mobile, 60, TimeUnit.SECONDS);

        Map<String, Object> r = new HashMap<>();
        r.put("msg", "发送校验码成功");
        r.put("code", 0);
        r.put("token", token);
        return r;
    }

    @PostMapping("/login")
    @ResponseBody //返回给客户端的是数据 不再是视图
    @CrossOrigin("*") // 允许所有的域名跨域访问该资源
    public Map<String, Object> login(String mobile, String password){
        Map<String,Object> map = new HashMap<>();
        Member member = memberMapper.getMember(mobile, password);
        if(member!=null){
            map.put("msg","登录成功");
            map.put("code",0);
        }else {
            map.put("msg","手机号或密码错误");
            map.put("code",1);
        }
        return map;
    }
}
