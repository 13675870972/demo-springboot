package com.example.demospringboot.config.filter.reuse;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.Enumeration;

@Slf4j
public class ConvertHttpServletRequestWrapper extends HttpServletRequestWrapper {
    public static final String POST_METHOD = "POST";
    private final String body;
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request
     * @throws IllegalArgumentException if the request is null
     */
    public ConvertHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        StringBuffer stringBuffer = new StringBuffer();
        String method = request.getMethod();
        log.info("请求方法 == {}", method);
        if (method.equalsIgnoreCase(POST_METHOD)) {
            log.info("进入处理POST...");
            try {
                @Cleanup BufferedReader bufferedReader = null;
                @Cleanup InputStream inputStream = request.getInputStream();
                if(inputStream!=null){
                    @Cleanup InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    bufferedReader = new BufferedReader(inputStreamReader);
                    char[] charBuffer = new char[128];
                    int readBytes = -1;
                    while((readBytes=bufferedReader.read(charBuffer))>0){
                        stringBuffer.append(charBuffer,0,readBytes);
                    }
                }else{
                    stringBuffer.append("");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            body = stringBuffer.toString();
        } else {
            log.info("进入处理GET...");
            body = request.getQueryString();
        }

        //统一转换成json
        log.info("【body】 == {}", body);

    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        @Cleanup final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        ServletInputStream servletInputStream = new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
            @Override
            public int read() throws IOException {
                   return byteArrayInputStream.read();
            }
         };
         return servletInputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return super.getParameterNames();
    }

    public String getBody(){
        return this.body;
    }
}
