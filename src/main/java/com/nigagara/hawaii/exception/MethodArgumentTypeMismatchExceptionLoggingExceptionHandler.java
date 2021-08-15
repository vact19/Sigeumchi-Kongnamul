package com.nigagara.hawaii.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class MethodArgumentTypeMismatchExceptionLoggingExceptionHandler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException e = (MethodArgumentTypeMismatchException)ex;
            // Do anything you want with it.
            // The method can be accessed using e.getParameter().getExecutable()
            log.error("Caught MethodArgumentTypeMismatchException method = {}",
                    e.getParameter().getExecutable(), e);
        }
        return null;
    }
}