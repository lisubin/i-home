package com.lsb116.ihome.service;

import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

// springboot在启动的时候 会扫描添加了该注解的类，并且创建该类的对象
// FileUploadService service = new FileUploadService();  控制反转
@Component
public class FileUploadService {

    @Value("${aliyun.endpoint}") //依赖注入
    private String endpoint;

    @Value("${aliyun.ak}")
    private String accessKeyId;

    @Value("${aliyun.sk}")
    private String accessKeySecret;

    @Value("${aliyun.img.host}")
    private String imgHost;



    public String upload(MultipartFile imgFile) throws IOException {

        //0000000000


        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        String fileName = imgFile.getOriginalFilename();

        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String uploadFileName = uuid + suffix;

        InputStream inputStream = imgFile.getInputStream();
        ossClient.putObject("i-home-02", uploadFileName, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
        //http://i-home-02.oss-cn-shenzhen.aliyuncs.com/273BBB1641194BDB94C620AD10110541.jpg

        //00000
        return imgHost + uploadFileName;
    }
}
