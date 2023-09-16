package org.tinycode.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo/server")
public class DServerController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/shou/server/info")
    public ResponseEntity getServerInfo() {
        JSONObject result = new JSONObject();
        result.put("serverName", "demo-server");
        result.put("requestUrl", "/demo/server/shou/server/info");
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/shou/server/loadbalancer/port")
    public ResponseEntity showLoadbalancerPort() {
        return new ResponseEntity("this server port is :" + port, HttpStatus.OK);
    }

}
