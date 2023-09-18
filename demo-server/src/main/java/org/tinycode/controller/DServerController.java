package org.tinycode.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tinycode.data.DClientResponseData;

import javax.servlet.http.HttpServletRequest;

@Slf4j
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
        log.info("there is server invoking by open feign");
        return new ResponseEntity("this server port is :" + port, HttpStatus.OK);
    }

    @PostMapping("/shou/client/port")
    public DClientResponseData showClientPort(HttpServletRequest response,
                                              @RequestParam("port") String port) {
        log.info("/shou/client/port port:{}", port);
        String user = response.getHeader("user");
        return new DClientResponseData("invoking port is :" + port + " and user is :" + user);
    }

}
