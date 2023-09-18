package org.tinycode.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
import org.tinycode.data.DClientResponseData;
import org.tinycode.feign.DServerFeignService;

@Slf4j
@RestController
@RequestMapping("/demo/client")
public class ClientController {

//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private DServerFeignService dServerFeignService;

    @GetMapping("/shou/server/info")
    public ResponseEntity getServerInfo() {
        JSONObject result = new JSONObject();
        result.put("serverName", "demo-client");
        result.put("requestUrl", "/demo/client/shou/server/info");
        return new ResponseEntity(result, HttpStatus.OK);
    }

//    @GetMapping("/shou/server/port/info")
//    public ResponseEntity getServerPort() {
//        String msg = restTemplate.getForObject("http://demo-server/demo/server/shou/server/loadbalancer/port", String.class);
//        return new ResponseEntity(msg, HttpStatus.OK);
//    }

    @GetMapping("/open/feign/demo/server")
    public ResponseEntity invokingServer() {
        log.info("this is openfeign invoking /open/feign/demo/server");
        DClientResponseData dClientResponseData = dServerFeignService.showClientPort("7001");
        return new ResponseEntity(dClientResponseData, HttpStatus.OK);
    }

}
