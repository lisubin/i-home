package com.lsb116.ihome.service;

import java.util.HashMap;
import java.util.Map;

public class DeleteService {

    public static Map<String,Object> delete(){
        Map<String,Object> map = new HashMap<>();
        map.put("msg","删除成功");
        map.put("code",0);
        // 重定向
        return map;
    }
}
