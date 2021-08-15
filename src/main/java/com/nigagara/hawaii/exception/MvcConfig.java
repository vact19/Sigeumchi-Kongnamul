package com.nigagara.hawaii.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@Slf4j
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(0, new MethodArgumentTypeMismatchExceptionLoggingExceptionHandler());
    }
}
// https://stackoverflow.com/questions/54436459/methodargumenttypemismatchexception-how-to-find-the-controller-method