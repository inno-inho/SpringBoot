package com.example.demo.Restful.C03Kakao;


import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@Slf4j
@RequestMapping("/Kakao/pay")
public class KakaoPayController {

    private String SECRET_KEY = "DEV2EBE0D1283AA2810388CEF664DF5F77D86027";

    // 단건결제 요청
    @GetMapping("/req")
    @ResponseBody
    public void req(){
        log.info("GET /Kakao/pay/req...");

        String url = "https://open-api.kakaopay.com/online/v1/payment/ready";

        RestTemplate restTemplate = new RestTemplate();

        // 요청 헤더 설정
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization","SECRET_KEY "+SECRET_KEY);
        header.add("Content-Type","application/json");

        // 요청 바디 파라미터(X)
        JSONObject params = new JSONObject();
        params.put("cid", "TC0ONETIME");
        params.put("partner_order_id", "1001");
        params.put("partner_user_id", "user10");
        params.put("item_name", "화이트화임");
        params.put("quantity", "3");
        params.put("total_amount", "4400");
        params.put("vat_amount", "400");
        params.put("tax_free_amount", "0");
        params.put("approval_url", "http://localhost:8095/Kakao/pay/success");         // 결제 성공시 REDIRECT_URL
        params.put("fail_url", "http://localhost:8095/Kakao/pay/fail");             // 결제 실패시 REDIRECT_URL
        params.put("cancel_url", "http://localhost:8095/Kakao/pay/cancel");           // 결제 취소시 REDIRECT_URL


        HttpEntity<JSONObject> entity = new HttpEntity(params, header);

        // 요청 후 응답 확인
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        System.out.println(response.getBody());

    }

    // 각각의 결제 상황이 끝나면 리다이렉트해서 보낼 곳
    @ResponseBody
    @GetMapping("/success")
    public void success(){
        log.info("GET/ Kakao/pay/success");
    }

    @ResponseBody
    @GetMapping("/fail")
    public void fail(){
        log.info("GET/ Kakao/pay/fail");
    }

    @ResponseBody
    @GetMapping("/cancel")
    public void cancel(){
        log.info("GET/ Kakao/pay/cancel");
    }

}
