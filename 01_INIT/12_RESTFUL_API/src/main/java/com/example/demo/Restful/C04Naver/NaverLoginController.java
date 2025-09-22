package com.example.demo.Restful.C04Naver;


import com.example.demo.Restful.C03Kakao.KakaoLoginController;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@Slf4j
@RequestMapping("/naver")
public class NaverLoginController {

    private String CLIENT_ID = "8GtSqzs2GMOpCNacuZqx";
    private String CLIENT_SECRET = "QDvbg8ble_";
    private String REDIRECT_URL = "http://localhost:8095/naver/getCode";

    private String code;
    private String state;
    private NaverTokenResponse naverTokenResponse;


    @GetMapping("/login")
    public String login(){
        log.info("GET /naver/login...");
        return "redirect:https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id="+CLIENT_ID+"&state=STATE_STRING&redirect_uri="+REDIRECT_URL;
    }

    @GetMapping("/getCode")     // redirect해서 인가 코드 받을 위치
    public String getCode(String code, String state) {     // 쿼리문으로 받는 파라미터
        log.info("GET /naver/getCode...code : " + code + " State: " + state);
        this.code = code;
        this.state = state;
        return "forward:/naver/getAccessToken";
    }

    @GetMapping("/getAccessToken")
    public String getAccessToken(){
        log.info("GET /naver/getAccessToken....");

        String url = "https://nid.naver.com/oauth2.0/token";

        RestTemplate restTemplate = new RestTemplate();     // 요청 준비

//        // 요청 헤더 설정(api에 맞춰야함)        // 토큰 요청에 있는 헤더
        HttpHeaders header = new HttpHeaders();

//        // 요청 바디 파라미터
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");  // 카카오에서 정해놓은거
        params.add("client_id",CLIENT_ID);   // 위에 설정해놓은거
        params.add("client_secret", CLIENT_SECRET);
        params.add("code",code);
        params.add("state",state);
//
        HttpEntity< MultiValueMap<String,String>  > entity = new HttpEntity<>(params,header);

        // 요청 후 응답 확인
        ResponseEntity<NaverTokenResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, NaverTokenResponse.class);
        System.out.println(response.getBody());

        this.naverTokenResponse = response.getBody();

        // main으로 리다이렉트
        return "redirect:/naver";          // redirect해서 정보 안보여줄거임

    }

    // 로그인 한 사람 정보 받아오기
    @GetMapping
    public String main(Model model){
        log.info("GET /naver/index...");

        String url = "https://openapi.naver.com/v1/nid/me";

        RestTemplate restTemplate = new RestTemplate();

        // 요청 헤더 설정
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization","Bearer "+ naverTokenResponse.getAccess_token());

        // 요청 바디 파라미터(X)

        HttpEntity entity = new HttpEntity(header);

        // 요청 후 응답 확인
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,entity, String.class);
        System.out.println(response.getBody());

        // 뷰로 전달

        return "naver/index";
    }



    //    ====================================
    //    naver token response class
    //    ====================================

    @Data
    private static class NaverTokenResponse{
        public String access_token;
        public String refresh_token;
        public String token_type;
        public String expires_in;
    }




}

