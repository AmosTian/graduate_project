package com.haoke.api.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Auspice Tian
 * @time 2021-03-31 9:09
 * @current haoke-manage-com.haoke.api.interceptor
 */

/*
* 替换Request对象
* */
@Component
public class RequestReplaceFilter extends OncePerRequestFilter {
    //继承该类，保证过滤器仅过滤一次请求
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!(request instanceof MyServletRequestWrapper)) {
            request = new MyServletRequestWrapper(request);
        }

        filterChain.doFilter(request, response);
    }
}
