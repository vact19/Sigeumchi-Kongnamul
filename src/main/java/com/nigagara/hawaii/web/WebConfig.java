package com.nigagara.hawaii.web;


import com.nigagara.hawaii.web.interceptor.LoginCheckInterceptor;
import com.nigagara.hawaii.web.interceptor.LogoutCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/user/myPage")
                .addPathPatterns("/recentTest");

        registry.addInterceptor(new LogoutCheckInterceptor())
                .order(1)
                .addPathPatterns("/user/login");
    }

}
