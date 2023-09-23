package org.tinycode.filter;

import org.tinycode.data.ApiResponse;
import org.tinycode.enmu.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class PreFilter extends BaseFilter implements Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String requestUri = request.getPath().pathWithinApplication().value();

        String authorization = request.getHeaders().getFirst(AUTHORIZATION);

        String method = request.getMethodValue();

        if (!("GET".equals(method) || "POST".equals(method))) {
            return getVoidMono(exchange,
                    ApiResponse.failed(ErrorCode.DISALLOWED_REQUEST_METHOD.getValue(), ErrorCode.DISALLOWED_REQUEST_METHOD.getName()), HttpStatus.METHOD_NOT_ALLOWED);
        }

        if (x_application_idIsIgnoreUrl(requestUri)) {
            return chain.filter(exchange);
        } else {
            String x_application_id = request.getHeaders().getFirst(X_APPLICATION_ID);
            if (StringUtils.isEmpty(x_application_id)) {
                return getVoidMono(exchange,
                        ApiResponse.failed(ErrorCode.X_APPLICATION_ID_IS_EMPTY.getValue(), ErrorCode.X_APPLICATION_ID_IS_EMPTY.getName()), HttpStatus.OK);
            }
            log.info("PreFilter url-->{} x_application_id -->{} ", requestUri, x_application_id);
            if (authorizationIsIgnoreUrl(requestUri)) {
                return chain.filter(exchange);
            } else {
                log.info("PreFilter url-->{} authorization -->{} ", requestUri, authorization);

                if (StringUtils.isEmpty(authorization)) {
                    return getVoidMono(exchange,
                            ApiResponse.failed(ErrorCode.AUTHORIZATION_IS_EMPTY.getValue(), ErrorCode.AUTHORIZATION_IS_EMPTY.getName()), HttpStatus.OK);
                }
            }
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
