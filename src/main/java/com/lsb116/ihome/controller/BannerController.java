package com.lsb116.ihome.controller;


import com.lsb116.ihome.entity.Banner;
import com.lsb116.ihome.mapper.BannerMapper;
import com.lsb116.ihome.service.DeleteService;
import com.lsb116.ihome.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//   http://127.0.0.1:8080/banner/list  列表

//   http://127.0.0.1:8080/banner/add GET   跳转到新增页面
//   http://127.0.0.1:8080/banner/add POST  新增数据

//   http://127.0.0.1:8080/banner/update GET   跳转到修改页面
//   http://127.0.0.1:8080/banner/update POST  保存修改数据


@Controller
@RequestMapping("/banner")
public class BannerController {

    @Autowired(required = false)
    BannerMapper bannerMapper;

    @Autowired
    FileUploadService fileUploadService; //注入文件上传服务


    @GetMapping("/update")
    public String update(Integer id, Model model) {
        model.addAttribute("banner", bannerMapper.getById(id));

        return "banner_update";
    }

    @PostMapping("/update")
    //@RequestMapping(value = "/update",method = RequestMethod.POST)
    public String update(Banner banner, MultipartFile imgFile) throws IOException {
        // 如果有上传新的文件
        if (!"".equals(imgFile.getOriginalFilename())) {
            String fileName = fileUploadService.upload(imgFile);
            // 设置新的图片路径
            banner.setImg(fileName);
        }

        bannerMapper.update(banner);
        return "redirect:/banner/list";
    }


    @GetMapping("/add")
    public String add() {
        return "banner_add";
    }


    /**
     * <input class="form-control" type="file" name="imgFile">
     * <p>
     * imgFile 对象会封装提交表单的时候 type="file" name="imgFile"数据
     *
     * @param banner
     * @param imgFile
     * @return
     */
    @PostMapping("/add")
    public String add(Banner banner, MultipartFile imgFile) throws IOException {
//        // 上传文件的真实姓名
//        String fileName = imgFile.getOriginalFilename();
//        System.out.println(fileName);
//        //上传文件到本地服务器
//        imgFile.transferTo(new File("E:\\img\\" + fileName));
        String fileName = fileUploadService.upload(imgFile);
        banner.setImg(fileName);
        bannerMapper.add(banner);
        return "redirect:/banner/list";
    }


    @GetMapping("/list")
    public String list(Model model) {

        List<Banner> list = bannerMapper.findAll();
        // 查询到数据如何传递给页面
        model.addAttribute("bannerList", list);

        //默认前缀 templates
        //默认后缀 .html 请求转发
        return "banner_list"; //templates/banner_list.html
    }

    @GetMapping("/delete")
    @ResponseBody
    public Map<String,Object> delete(@RequestParam("id") Integer bannerId) {
        bannerMapper.deleteById(bannerId);
        return DeleteService.delete();
    }

}
