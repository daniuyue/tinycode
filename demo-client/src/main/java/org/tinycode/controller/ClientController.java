package org.tinycode.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo/client")
public class ClientController {

    @GetMapping("/shou/server/info")
    public ResponseEntity getServerInfo() {
        JSONObject result = new JSONObject();
        result.put("serverName", "demo-client");
        result.put("requestUrl", "/demo/client/shou/server/info");
        return new ResponseEntity(result, HttpStatus.OK);
    }

}
