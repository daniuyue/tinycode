package org.tinycode.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tinycode.data.DClientResponseData;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("tiny/server")
public class ManageController {

    @PostMapping("/invoke/port")
    public DClientResponseData showClientPort(HttpServletRequest response,
                                              @RequestParam("port") String port) {
        log.info("/shou/client/port port:{}", port);
        String user = response.getHeader("user");
        return new DClientResponseData("invoking port is :" + port + " and user is :" + user);
    }


}
