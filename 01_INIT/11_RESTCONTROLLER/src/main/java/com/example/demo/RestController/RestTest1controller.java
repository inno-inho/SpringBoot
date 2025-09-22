package com.example.demo.RestController;


import com.example.demo.Dto.MemoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/rest")
public class RestTest1controller {

    @GetMapping(value="/getText", produces = MediaType.TEXT_PLAIN_VALUE)
    public String t1(){
        log.info("GET /rest/getTest...");
        return "Hello world";
    }


    // Json 형식
    @GetMapping(value="/getJson", produces = MediaType.APPLICATION_JSON_VALUE)
    public MemoDto t2(){
        log.info("GET /rest/getJson...");
        return new MemoDto(1L, "TEXT-1", "WRITER-1", LocalDateTime.now());
    }

    // XML 형식
    @GetMapping(value="/getXml", produces = MediaType.APPLICATION_XML_VALUE)
    public MemoDto t3(){
        log.info("GET /rest/getXML...");
        return new MemoDto(1L, "TEXT-1", "WRITER-1", LocalDateTime.now());
    }

    // XML을 List형식으로 받는법
    @GetMapping(value="/getXmlList", produces = MediaType.APPLICATION_XML_VALUE)
    public List<MemoDto> t4(){
        log.info("GET /rest/getXmlList...");
        List<MemoDto> list = new ArrayList();
        for (long i =1; i<= 100; i++){
            MemoDto tmp = new MemoDto(i, "내용-" +i, "작성자-" +i, LocalDateTime.now());
            list.add(tmp);
        }
        return list;
    }


    // 리스트형으로 Json데이터 받기
    @GetMapping(value="/getJsonList", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MemoDto> t4_1(){
        log.info("GET /rest/getJsonList...");
        List<MemoDto> list = new ArrayList();
        for (long i =1; i<= 100; i++){
            MemoDto tmp = new MemoDto(i, "내용-" +i, "작성자-" +i, LocalDateTime.now());
            list.add(tmp);
        }
        return list;
    }

    @GetMapping(value="/getXmlList2/{show}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity< List <MemoDto> > t5(
            @PathVariable("show")  boolean show
    ) {
        log.info("GET /rest/getXmlList2...show : " + show);
        if (show) {
            List<MemoDto> list = new ArrayList();
            for (long i = 1; i <= 100; i++) {
                MemoDto tmp = new MemoDto(i, "내용-" + i, "작성자-" + i, LocalDateTime.now());
                list.add(tmp);
            }
//            return new ResponseEntity("상태정보값", "데이터값")
            return ResponseEntity.status(HttpStatus.OK).body(list);

        }
        return new ResponseEntity(null ,HttpStatus.BAD_GATEWAY);
    }

}
