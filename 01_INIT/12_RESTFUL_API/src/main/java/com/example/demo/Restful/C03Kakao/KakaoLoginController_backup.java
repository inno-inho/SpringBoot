//package com.example.demo.Restful.C03Kakao;
//
//
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.json.simple.JSONObject;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Date;
//
//
//@Controller
//@Slf4j
//@RequestMapping("/Kakao")
//public class KakaoLoginController_backup {
//
//    private String CLIENT_ID="e10b9e492af55d5a4bcb9afc491c1f9d";
////    private String REDIRECT_URI="http://localhost:8095/Kakao/getCode";
////    private String LOGOUT_REDIRECT_URI="http://localhost:8095/Kakao";
//
//
//    // 메시지 주고받기위해서는 리다이렉트 경로를 상대방 아이피로 맞춰야암
//    private String REDIRECT_URI="http://192.168.5.7:8095/Kakao/getCode";
//
//    private String LOGOUT_REDIRECT_URI="http://192.168.5.7:8095/Kakao";
//
//
//    private String code;
//    private KakaoTokenResponse kakaoTokenResponse;      // 토큰 보관용
//
//    @GetMapping("/login")
//    public String login(){
//
//        log.info("GET /kakao/login....");
//        return  "redirect:https://kauth.kakao.com/oauth/authorize?" +   // GET(토큰내놔)
//                "client_id=" + CLIENT_ID+"&redirect_uri="+REDIRECT_URI+"&response_type=code";
//    }
//
//    @GetMapping("/getCode")     // redirect해서 인가 코드 받을 위치
//    public String getCode(String code){   // 쿼리문으로 받는 파라미터
//
//        log.info("GET /Kakao/getCode....code"+ code);
//        this.code = code;
//        return "forward:/Kakao/getAccessToken";     // forward: 주소창은 여전히 getCode로
//    }
//
//    @GetMapping("/getAccessToken")
//    public String getAccessToken(){
//        log.info("GET /Kakao/getAccessToken...");
//
//        String url ="https://kauth.kakao.com/oauth/token";
//
//        RestTemplate restTemplate = new RestTemplate();     // 요청 준비
//
//        // 요청 헤더 설정(x)  (api에 맞춰야함)     // 토큰 요청에 있는 헤더
//        HttpHeaders header = new HttpHeaders();
//        header.add("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
//
//         // 요청 바디 설정(x)
//        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type","authorization_code");  // 카카오에서 정해놓은거
//        params.add("client_id",CLIENT_ID);    // 위에 설정해놓은거
//        params.add("redirect_uri",REDIRECT_URI);
//        params.add("code", code);
//
//        HttpEntity< MultiValueMap<String,String>  > entity = new HttpEntity<>(params,header);
//
//        // 요청 후 응답 확인
//        ResponseEntity<KakaoTokenResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, KakaoTokenResponse.class);
//        System.out.println(response.getBody());
//        this.kakaoTokenResponse = response.getBody();
//
//        // main으로 리다이렉트
//        return "redirect:/Kakao";       // 로그인하면 정보 들고 kakao 메인페이지로 리다이렉트(/Kakao/login URL은 안바뀜)
//
//    }
//
//    @GetMapping
//    public String main(Model model){
//        log.info("GET /Kakao/index...");
//
//        String url ="https://kapi.kakao.com/v2/user/me";
//
//        RestTemplate restTemplate = new RestTemplate();     // 요청 준비
//
//        // 요청 헤더 설정(x)  (api에 맞춰야함)     // 토큰 요청에 있는 헤더
//        HttpHeaders header = new HttpHeaders();
//        header.add("Authorization","Bearer " + kakaoTokenResponse.getAccess_token());
//        header.add("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
//
//        // 요청 바디 파라미터 없음
//        HttpEntity entity = new HttpEntity(header);
//
//        // 요청 후 응답 확인
//        ResponseEntity<KakaoProfileResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, KakaoProfileResponse.class);
//        System.out.println(response.getBody());
//
//        // 뷰로 전달
//        model.addAttribute("profile", response.getBody());
//
//        String nickname = response.getBody().getProperties().getNickname();
//        String image_url = response.getBody().getProperties().getProfile_image();
//        String email = response.getBody().getKakao_account().getEmail();
//
//        model.addAttribute("nickname",nickname);        // model로 던지는거는 스프링부트에서 프론트엔드도 처리할때임
//        model.addAttribute("image_url",image_url);      // 백엔드 프론트엔드 나눌거면 rest로 던져줘야함
//        model.addAttribute("email",email);
//
//
//        return "Kakao/index";
//    }
//
//
//
//    // 로그아웃(엑세스토큰만 만료)
//    // 로그아웃하고나서 메인페이지 갈려고 하면 문제가 생김
//    // 그리고 로그인 화면으로 가면 액세스토큰만 만료이기때문에 다시 로그인이 되버림
//    @GetMapping("/logout1")
//    @ResponseBody   // 비동기
//    public void logout1(){
//        log.info("GET /Kakao/logout1");
//
//        String url ="https://kapi.kakao.com/v1/user/logout";
//
//        RestTemplate restTemplate = new RestTemplate();     // 요청 준비
//
//        // 요청 헤더 설정(x)  (api에 맞춰야함)     // 토큰 요청에 있는 헤더
//        HttpHeaders header = new HttpHeaders();
//        header.add("Authorization","Bearer " + kakaoTokenResponse.getAccess_token());
//
//        // 요청 바디 파라미터 없음
//        HttpEntity entity = new HttpEntity(header);
//
//        // 요청 후 응답 확인
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
//        System.out.println(response.getBody());
//    }
//
//    // Unlink 연결해제(엑세스토큰 만료/ 리프레시토큰 만료/ 동의 철회)
//    // 아이디 비번은 안 물어봄
//    @GetMapping("/logout2")
//    @ResponseBody   // 비동기
//    public void logout2(){
//        log.info("GET /Kakao/logout2");
//
//        String url ="https://kapi.kakao.com/v1/user/unlink";
//
//        RestTemplate restTemplate = new RestTemplate();     // 요청 준비
//
//        // 요청 헤더 설정(x)  (api에 맞춰야함)     // 토큰 요청에 있는 헤더
//        HttpHeaders header = new HttpHeaders();
//        header.add("Authorization","Bearer " + kakaoTokenResponse.getAccess_token());
//
//        // 요청 바디 파라미터 없음
//        HttpEntity entity = new HttpEntity(header);
//
//        // 요청 후 응답 확인
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
//        System.out.println(response.getBody());
//    }
//
//
//    @GetMapping("/logout3")                              // 동기처리임
//    public String logout3(){      // 카카오 개발자 페이지의 카카오 로그인 고급의 리다이렉트 url 등록해야함
//        // 계정 자체를 로그아웃
//        log.info("GET /Kakao/logout3");
//
//        return "redirect:https://kauth.kakao.com/oauth/logout?client_id="+CLIENT_ID+"&logout_redirect_uri="+LOGOUT_REDIRECT_URI;
//    }
//
////    ===============================
////    카카오 메시지 보내기
////    =================================
//
//    @GetMapping("/getMsgCode")
//    public String getMsgCode(){
//        log.info("Get /Kakao/getMsgCode...");
//        return "redirect:https://kauth.kakao.com/oauth/authorize?client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URI+"&response_type=code&scope=talk_message,friends";
//    }
//
//    @GetMapping("/message/me/{message}")
//    @ResponseBody
//    public void message_me(@PathVariable String message){
//        String url ="https://kapi.kakao.com/v2/api/talk/memo/default/send";
//
//        RestTemplate restTemplate = new RestTemplate();     // 요청 준비
//
//        // 요청 헤더 설정(x)  (api에 맞춰야함)     // 토큰 요청에 있는 헤더
//        HttpHeaders header = new HttpHeaders();
//        header.add("Authorization","Bearer " + kakaoTokenResponse.getAccess_token());
//        header.add("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
//
//        // 요청 바디 파라미터
//        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
//
//        JSONObject template_object = new JSONObject();
//        template_object.put("object_type","text");
//        template_object.put("text",message);    // 입력한 message
//        template_object.put("link",new JSONObject());
//        template_object.put("button_title","");
//
//        params.add("template_object", template_object.toString());     // template_object.toString()의 파라미터는 String이기때문에
//
//
//        HttpEntity< MultiValueMap<String,String> > entity = new HttpEntity(params, header);
//
//        // 요청 후 응답 확인
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
//        System.out.println(response.getBody());
//    }
//
//
//    @GetMapping("/friends")
//    @ResponseBody
//    public void getFriends(){
//        log.info("Get /Kakao/friends");
//
//        String url ="https://kapi.kakao.com/v1/api/talk/friends";
//
//        RestTemplate restTemplate = new RestTemplate();     // 요청 준비
//
//        // 요청 헤더 설정(x)  (api에 맞춰야함)     // 토큰 요청에 있는 헤더
//        HttpHeaders header = new HttpHeaders();
//        header.add("Authorization","Bearer " + kakaoTokenResponse.getAccess_token());
//
//
//        // 요청 바디 파라미터 X
//
//        HttpEntity entity = new HttpEntity(header);
//
//        // 요청 후 응답 확인
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//        System.out.println(response.getBody());
//
//    }
//
//
//
//
//
//
//
//
//
//
//    // ======================================
//    //        카카오 access 토큰
//    // ======================================
//
//    @Data
//    private static class KakaoTokenResponse{
//        public String access_token;
//        public String token_type;
//        public String refresh_token;
//        public int expires_in;
//        public String scope;
//        public int refresh_token_expires_in;
//    }
//
//    // ======================================
//    //        카카오 프로필 클래스
//    // ======================================
//
//    @Data
//    private static class KakaoAccount{
//        public boolean profile_nickname_needs_agreement;
//        public boolean profile_image_needs_agreement;
//        public Profile profile;
//        public boolean has_email;
//        public boolean email_needs_agreement;
//        public boolean is_email_valid;
//        public boolean is_email_verified;
//        public String email;
//    }
//
//    @Data
//    private static class Profile{
//        public String nickname;
//        public String thumbnail_image_url;
//        public String profile_image_url;
//        public boolean is_default_image;
//        public boolean is_default_nickname;
//    }
//
//    @Data
//    private static class Properties{
//        public String nickname;
//        public String profile_image;
//        public String thumbnail_image;
//    }
//
//    @Data
//    private static class KakaoProfileResponse{
//        public long id;
//        public Date connected_at;
//        public Properties properties;
//        public KakaoAccount kakao_account;
//    }
//
//
//
//
//
//}
