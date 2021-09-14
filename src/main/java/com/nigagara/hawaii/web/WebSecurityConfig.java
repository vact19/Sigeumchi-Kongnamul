package com.nigagara.hawaii.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    //private final PasswordEncoder encoder; 를 사용해 구현체를 Autowire해 씀
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder(); // 구현체 BCrypt
    }

    // 경로에 더블 슬래시  //를 허용하는 Firewall을 Bean으로 등록
    @Bean
    public HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();
    }
    // Firewall 등록
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.httpFirewall(defaultHttpFirewall());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()      // cors 비활성화
                .csrf().disable()      // csrf 비활성화
                //.formLogin().disable() //기본 로그인 페이지 없애기
                .headers().frameOptions().disable();
    }
}
