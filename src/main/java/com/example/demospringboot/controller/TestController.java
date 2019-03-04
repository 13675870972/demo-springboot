package com.example.demospringboot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.demospringboot.service.SayHello;
import com.example.demospringboot.util.HttpUtil;
import com.example.demospringboot.util.ParamSecurityUtil;
import com.example.demospringboot.util.RequestUtil;
import com.example.demospringboot.vo.GateWayReqStatementReqVO;
import com.example.demospringboot.vo.ResultVO;
import com.example.demospringboot.vo.SecurityParamVO;
import com.example.demospringboot.vo.TestBean;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;

@Slf4j
@RestController
public class TestController {

    @Resource(name = "dogSayHello")
    private SayHello dogSayHello;

    @Resource(name = "catSayHello")
    private SayHello catSayHello;
    //家装商户产品
    private String[] homeProductCode = {"18", "19", "20"};
    private String homeSecretKey = "b0bdb64981caa117e9f344825b1b5f73";
    private String homePublicKey = "f6452894a8846dfc";
    private String homeMerchantId = "36";
    private String homeChildMerchantId = "38";
    //    @Value("${gateway.user.limit.credit}")
    private String gatewayUrlPrefix = "https://xyapps.yongdapay.com:17788";

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/testStr")
    public String testStr(TestBean testBean, HttpServletRequest request) {
        String parameterMap = RequestUtil.getParameterMapString(request);
        String parameterMap2 = RequestUtil.getParameterMapString(request);
        System.err.println("parameterMap == " + parameterMap);
        System.err.println("parameterMap2 == " + parameterMap2);
        StringBuffer stringBuffer = new StringBuffer();
        try {
            @Cleanup BufferedReader bufferedReader = null;
            @Cleanup InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                @Cleanup InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);
                char[] charBuffer = new char[128];
                int readBytes = -1;
                while ((readBytes = bufferedReader.read(charBuffer)) > 0) {
                    stringBuffer.append(charBuffer, 0, readBytes);
                }
            } else {
                stringBuffer.append("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String body = stringBuffer.toString();
        System.err.println(body);
        System.err.println(RequestUtil.getParameterAllMap(request));

        return body;
    }

    @RequestMapping("/download")
    public void download(HttpServletResponse response) throws IOException, ParseException {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=user.xls");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //构建请求参数
        GateWayReqStatementReqVO paramVO = GateWayReqStatementReqVO.builder()
                .merchantId("36")
                .accountDate(format.parse("2019-01-04"))
                .tradeType("1")
                .build();

        paramVO.setMerchantId(homeMerchantId);
        String secureKey = ParamSecurityUtil.getParamSecure(paramVO, homePublicKey, homeSecretKey);
        SecurityParamVO<GateWayReqStatementReqVO> securityParamVO = SecurityParamVO.of(secureKey, homePublicKey, paramVO);

        //请求网关
        String url = gatewayUrlPrefix + "/user/downloadStatement";
        try {
            log.info("查询账单参数 == {}", JSON.toJSONString(securityParamVO));
            HttpUtil.Response httpResp = HttpUtil.post(url, JSON.toJSONString(securityParamVO));
            log.info("查询账单结果 == {}", JSON.toJSONString(httpResp));

            String contentStr = httpResp.getContent();
            log.info("查询账单内容 == {}", contentStr);

            JSONObject contentJson = JSONObject.parseObject(contentStr);
            log.info("查询账单对象 == {}", contentJson);

            String resultStr = contentJson.getString("result");
            log.info("查询账单对象resultStr == {}", resultStr);

            byte[] resultByte = Base64.getDecoder().decode(resultStr);
            log.info("文件大小 == {} b", resultByte.length);

            @Cleanup OutputStream outputStream = response.getOutputStream();
            outputStream.write(resultByte);
        } catch (Exception e) {
            log.error("{}", e);
        }
    }

    @RequestMapping("/hi")
    @ResponseBody
    public String sayHello(String num) {
        System.err.println("num = " + num);
        if ("1".equals(num)) {
            return dogSayHello.say();
        }
        return catSayHello.say();
    }

    public static void main(String[] args) {
        try {
            int a = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("好");

        }
        System.err.println("完毕");
    }


}
