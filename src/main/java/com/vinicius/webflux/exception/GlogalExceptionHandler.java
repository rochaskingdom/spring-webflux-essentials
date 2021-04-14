package com.vinicius.webflux.exception;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.STACK_TRACE;

@Order(-2)
@Component
public class GlogalExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GlogalExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
                                  ApplicationContext applicationContext, ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, resourceProperties, applicationContext);
        this.setMessageWriters(serverCodecConfigurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::formatErrorResponse);
    }

    private Mono<ServerResponse> formatErrorResponse(ServerRequest request) {
        String query = request.uri().getQuery();
        ErrorAttributeOptions errorAttr = isTraceEnable(query) ? ErrorAttributeOptions.of(STACK_TRACE) : ErrorAttributeOptions.defaults();
        Map<String, Object> errorAttributesMap = getErrorAttributes(request, errorAttr);
        int status = (int) Optional.ofNullable(errorAttributesMap.get("status")).orElse(500);

        return ServerResponse.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(errorAttributesMap));
    }

    private boolean isTraceEnable(String query) {
        return String.valueOf(query).isEmpty() && query.contains("trace=true");
    }
}
