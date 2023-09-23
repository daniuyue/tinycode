package org.tinycode.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tinycode.data.DClientResponseData;


@FeignClient(name = "tiny-server",path = "tiny/server")
public interface DServerFeignService {

    @PostMapping("/invoke/port")
    public DClientResponseData showClientPort(@RequestParam("port") String port);
}
