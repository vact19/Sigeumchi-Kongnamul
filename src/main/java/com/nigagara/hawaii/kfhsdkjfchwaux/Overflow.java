package com.nigagara.hawaii.kfhsdkjfchwaux;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;

public class Overflow {

    public void 스프링이_없어요(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();// Jackson 라이브러리
        ServletInputStream inputStream = request.getInputStream();
        String requestBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        Data data = objectMapper.readValue(requestBody, Data.class);
        System.out.println("data = " + data.getAge());
    }
    static class Data{
        private int age;
        public int getAge() { return age; }
    }
}

