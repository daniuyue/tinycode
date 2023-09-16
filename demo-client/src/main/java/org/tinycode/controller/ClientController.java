package org.tinycode.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/demo/client")
public class ClientController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/shou/server/info")
    public ResponseEntity getServerInfo() {
        JSONObject result = new JSONObject();
        result.put("serverName", "demo-client");
        result.put("requestUrl", "/demo/client/shou/server/info");
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/shou/server/port/info")
    public ResponseEntity getServerPort() {
        String msg = restTemplate.getForObject("http://demo-server/demo/server/shou/server/loadbalancer/port", String.class);
        return new ResponseEntity(msg, HttpStatus.OK);
    }

}
