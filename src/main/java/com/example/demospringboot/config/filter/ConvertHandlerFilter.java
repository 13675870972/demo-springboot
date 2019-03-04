package com.example.demospringboot.config.filter;

import com.example.demospringboot.config.filter.reuse.ConvertHttpServletRequestWrapper;
import com.example.demospringboot.util.HttpHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: caoyc
 * @Date: 2018/6/21 下午2:00
 * @Description:
 */
@Slf4j
//@Configuration
public class ConvertHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("进入过滤器...");

        // 防止流读取一次后就没有了, 所以需要将流继续写出去，提供后续使用
//        ServletRequest requestWrapper = new ConvertHttpServletRequestWrapper(request);
//        String json = HttpHelper.getBodyString(requestWrapper);
//        log.info("进入过滤器...json == {}", json);
//
//        filterChain.doFilter(requestWrapper, response);
    }
}
