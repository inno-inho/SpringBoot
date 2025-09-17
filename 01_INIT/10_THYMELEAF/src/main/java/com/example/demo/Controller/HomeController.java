package com.example.demo.Controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller     // application Context라고 하는 영역안에 bean이라고 하는 객체를 만든대
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(){
//        System.out.println("GET /");
        log.info("GET /...");
        return"index";
    }
}
