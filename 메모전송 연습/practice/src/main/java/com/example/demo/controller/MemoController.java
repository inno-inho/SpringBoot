package com.example.demo.controller;


import com.example.demo.domain.dto.MemoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/memo")
public class MemoController {

    @GetMapping("/memoForm")
    public void add_get(){
        log.info("Get /memo/memoForm");
    }

    @PostMapping("/memoForm")
    public void add_post(MemoDto dto){
        log.info("post /memo/memoForm..." + dto );
    }
}
