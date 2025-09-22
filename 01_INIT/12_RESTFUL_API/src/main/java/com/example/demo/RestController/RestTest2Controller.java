package com.example.demo.RestController;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequestMapping("/rest2")
// 동기 방식
public class RestTest2Controller {
    @GetMapping("/test1")
    @ResponseBody // 이게 있으니까 페이지가 안 만들어져 있어도 데이터가 전송됌
        public String t1(){
        log.info("GET /rest2/test1...");
        return "Hello world";
    }

}
