package com.lsb116.ihome.controller;



import com.lsb116.ihome.entity.Member;
import com.lsb116.ihome.mapper.MemberMapper;
import com.lsb116.ihome.service.DeleteService;
import com.lsb116.ihome.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//   http://127.0.0.1:8080/member/list  列表

//   http://127.0.0.1:8080/member/add GET   跳转到新增页面
//   http://127.0.0.1:8080/member/add POST  新增数据

//   http://127.0.0.1:8080/member/update GET   跳转到修改页面
//   http://127.0.0.1:8080/member/update POST  保存修改数据


@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired(required = false)
    MemberMapper memberMapper;

    @Autowired
    FileUploadService fileUploadService; //注入文件上传服务


    @GetMapping("/update")
    public String update(Integer id, Model model) {
        model.addAttribute("member", memberMapper.getById(id));
        return "member_update";
    }

    @PostMapping("/update")
    //@RequestMapping(value = "/update",method = RequestMethod.POST)
    public String update(Member member) throws IOException {
        // 如果有上传新的文件
        memberMapper.update(member);

        return "redirect:/member/list";
    }


    @GetMapping("/add")
    public String add() {
        return "member_add";
    }


    /**
     * <input class="form-control" type="file" name="imgFile">
     * <p>
     * imgFile 对象会封装提交表单的时候 type="file" name="imgFile"数据
     *
     * @param member
     * @param imgFile
     * @return
     */
    @PostMapping("/add")
    public String add(Member member, MultipartFile imgFile) throws IOException {
//        // 上传文件的真实姓名
//        String fileName = imgFile.getOriginalFilename();
//        System.out.println(fileName);
//        //上传文件到本地服务器
//        imgFile.transferTo(new File("E:\\img\\" + fileName));
        String fileName = fileUploadService.upload(imgFile);
        return "redirect:/member/list";
    }


    @GetMapping("/list")
    public String list(Model model) {

        List<Member> list = memberMapper.findAll();
        // 查询到数据如何传递给页面
        model.addAttribute("list", list);

        //默认前缀 templates
        //默认后缀 .html 请求转发
        return "member_list"; //templates/member_list.html
    }

    @GetMapping("/delete")
    @ResponseBody
    public Map<String,Object> delete(@RequestParam("id") Integer memberId) {
         memberMapper.deleteById(memberId);
        return DeleteService.delete();
    }

}
