package com.lsb116.ihome.controller;


import com.lsb116.ihome.entity.User;
import com.lsb116.ihome.mapper.UserMapper;
import com.lsb116.ihome.service.DeleteService;
import com.lsb116.ihome.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.comparator.ComparableComparator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
//   http://127.0.0.1:8080/user/list  列表

//   http://127.0.0.1:8080/user/add GET   跳转到新增页面
//   http://127.0.0.1:8080/user/add POST  新增数据

//   http://127.0.0.1:8080/user/update GET   跳转到修改页面
//   http://127.0.0.1:8080/user/update POST  保存修改数据


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired(required = false)
    UserMapper userMapper;

    @Autowired
    FileUploadService fileUploadService; //注入文件上传服务


    @GetMapping("/update")
    public String update(Integer id, Model model) {
        model.addAttribute("user", userMapper.getById(id));

        return "user_update";
    }

    @PostMapping("/update")
    //@RequestMapping(value = "/update",method = RequestMethod.POST)
    public String update(User user) {
        userMapper.update(user);
        return "redirect:/user/list";
    }


    @GetMapping("/add")
    public String add() {
        return "user_add";
    }

    @PostMapping("/add")
    //@RequestMapping(value = "/update",method = RequestMethod.POST)
    public String add(User user) {
        userMapper.add(user);
        return "redirect:/user/list";
    }


    @GetMapping("/list")
    public String list(Model model) {

        List<User> list = userMapper.findAll();
        // 查询到数据如何传递给页面
        model.addAttribute("list", list);

        //默认前缀 templates
        //默认后缀 .html 请求转发
        return "user_list"; //templates/user_list.html
    }

    @GetMapping("/delete")
    @ResponseBody
    public Map<String,Object> delete(@RequestParam("id") Integer userId) {
        userMapper.deleteById(userId);
        return DeleteService.delete();
    }

}
