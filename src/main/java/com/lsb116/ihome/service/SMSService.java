package com.lsb116.ihome.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SMSService {

    @Value("${aliyun.ak}")
    private String accessKeyId;

    @Value("${aliyun.sk}")
    private String accessKeySecret;

    public String getCode(String mobile) throws ClientException {


        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi",
                "dysmsapi.aliyuncs.com");
        IAcsClient acsClient = new DefaultAcsClient(profile);

        SendSmsRequest request = new SendSmsRequest();
        request.setMethod(MethodType.POST);
        request.setPhoneNumbers(mobile);

        request.setSignName("E体检");
        request.setTemplateCode("SMS_116560495");


        Random r = new Random();
        StringBuffer buffer = new StringBuffer();
        //生成一个[0,9]整数
        for (int i = 0; i < 5; i++) {
            //System.out.println( r.nextInt(10));
            buffer.append(r.nextInt(10));
        }
        String code = buffer.toString();
        System.out.println(code);
        request.setTemplateParam("{\"code\":" + code + "}");
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        System.out.println(sendSmsResponse.getMessage());

        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            System.out.println("短信发送成功");
            return code;

        }
        return null;
    }
}
