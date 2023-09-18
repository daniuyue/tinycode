package org.tinycode.interceptor.fegin;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DServerFeignInterceptor  implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
         log.info("进入 demo-server feign自定义拦截器");
        // 我们可以在这里记录日志或者添加参数以及修改参数等
        requestTemplate.header("user", "user001");
        //添加参数 会出两个 /shou/client/port port:7001,8888
//        requestTemplate.query("port", "8888");
    }
}
