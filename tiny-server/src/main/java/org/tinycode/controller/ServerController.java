package org.tinycode.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/tiny/server")
public class ServerController {

    @Value("${server.port}")
    private String port;

    /**
     * 查看服务信息
     */
    @GetMapping("/info")
    public ResponseEntity getServerInfo() {
        JSONObject result = new JSONObject();
        result.put("serverName", "demo-server");
        result.put("requestUrl", "/demo/server/shou/server/info");
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * 查看负载端口号信息
     */
    @GetMapping("/loadbalancer/port")
    public ResponseEntity showLoadbalancerPort() {
        log.info("there is server invoking by open feign");
        return new ResponseEntity("this server port is :" + port, HttpStatus.OK);
    }


}
