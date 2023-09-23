package org.tinycode.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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
@RequestMapping("/tiny/manage")
@RefreshScope //支持Nacos的动态刷新
public class ManageController {

//    @Autowired
//    private RestTemplate restTemplate;

    @Value("${config.info}") // 从nacos中取
    private String configInfo;

    @Value("${config.common}") // 从nacos中取
    private String configCommon;

    @Autowired
    private DServerFeignService dServerFeignService;

    /**
     * 查看服务信息
     */
    @GetMapping("/shou/server/info")
    public ResponseEntity getServerInfo() {
        JSONObject result = new JSONObject();
        result.put("serverName", "tiny-manage");
        result.put("requestUrl", "/tiny/manage/shou/server/info");
        return new ResponseEntity(result, HttpStatus.OK);
    }

//    @GetMapping("/shou/server/port/info")
//    public ResponseEntity getServerPort() {
//        String msg = restTemplate.getForObject("http://demo-server/demo/server/shou/server/loadbalancer/port", String.class);
//        return new ResponseEntity(msg, HttpStatus.OK);
//    }

    /**
     * 服务间调用测试
     */
    @GetMapping("/open/feign/demo/server")
    public ResponseEntity invokingServer() {
        log.info("this is openfeign invoking /open/feign/demo/server");
        DClientResponseData dClientResponseData = dServerFeignService.showClientPort("7001");
        return new ResponseEntity(dClientResponseData, HttpStatus.OK);
    }

    /**
     * 读取 nacos 注册中心 配置
     */
    @GetMapping("/nacos/config/info")
    public ResponseEntity getNacosInfo() {
        log.info("request url :/nacos/config/info");
        DClientResponseData dClientResponseData = new DClientResponseData(" configInfo:" + configInfo + " configCommon:" + configCommon);
        return new ResponseEntity(dClientResponseData, HttpStatus.OK);
    }

}
