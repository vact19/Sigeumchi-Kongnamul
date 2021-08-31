package com.nigagara.hawaii.web;

import com.nigagara.hawaii.web.interceptor.Email_Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Email_Interceptor())
                .order(1)
                .addPathPatterns("/user/new");
    }
}
