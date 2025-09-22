package com.example.demo.Restful.C03Kakao;

//카카오 채널 가입 버튼컨트롤러

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/Kakao")
public class KakaoChannelController {
    @GetMapping("/channel")
    public void channel(){
        log.info("GET /Kakao/channel...");
    }

}
