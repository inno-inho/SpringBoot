package com.example.demo.Restful.C01Opendata;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;


// 대구광역시 돌발 교통정보 조회 서비스
@Controller
@Slf4j
@RequestMapping("/BUS")
public class OpenData03Controller {

//    getRealtime02 버스 실시간 도착정보
    private String server = "https://apis.data.go.kr/6270000/dbmsapi02/getRealtime02";      // 공공데이터의 엔드포인트 잡아서 설정하기
//  https://apis.data.go.kr/6270000/dbmsapi02/getRealtime02?serviceKey=3bf653831dbac6f912c7cf62b9fc65dd9b5f6992dc7c29c327f2432b05901b1f&bsId=7001001600&routeNo=649 이었음
    private String serviceKey = "3bf653831dbac6f912c7cf62b9fc65dd9b5f6992dc7c29c327f2432b05901b1f";     // 공공데이터의 일반 인증키
    private String bsId;
    private String routeNo;




    //정류소 ID : 7001001600 (반월당역(2번출구))
    //버스번호 : 649
    //버스노선ID : 3000649000
    @GetMapping(value = "/{bsId}/{routeNo}")
    public String get(
        @PathVariable(value = "bsId", required = true) String bsId,
        @PathVariable(value = "routeNo", required = true) String routeNo,
        Model model
    )
    {
        log.info("GET // Incident ...bsId: " + bsId + ", routeNo: " +routeNo);
        this.bsId = bsId;
        this.routeNo = routeNo;


        // 파라미터 설정(service key 포함)
//        String url = UriComponentsBuilder.fromHttpUrl(server)       // 얘가 URL에 적히는 파라미터 값 대신함
//                .queryParam("serviceKey", "serviceKey" )              // 왜인지 이거는 안됬음
//                .queryParam("pageNo", pageNo)
//                .queryParam("numOfRows", numOfRows)
//                .toUriString();

        String url = server;        // 얘가 URL에 적히는 파라미터 값 대신함
        url+= "?serviceKey=" + serviceKey;
        url+="&bsId=" + bsId;
        url+="&routeNo="+routeNo;

        RestTemplate restTemplate = new RestTemplate();     // 요청 준비


        // Post방식이라면 여기것도 씀
        // 요청 헤더 설정(x)  (api에 맞춰야함)
//        HttpHeaders header = new HttpHeaders();
//        header.add("key","value");
//        // 요청 바디 설정(x)
//        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
//        params.add("","");
//
//        HttpEntity< MultiValueMap<String,String>  > entity = new HttpEntity<>(params,header);
        // 요청 후 응답 확인
        ResponseEntity<Root> response = restTemplate.exchange(url, HttpMethod.GET, null, Root.class);

        // Rest Type을 clsss type으로 반환

        System.out.println(response.getBody());

        // 확인

        Root root = response.getBody();
        Body body = root.getBody();
        ArrayList<Item> items = body.getItems();
        items.forEach((item) -> {
            System.out.println(item);
        });


        model.addAttribute("list", items);      // 뷰로 넘겨주기


        return "Opendata/index3";



    }


//        json으로 받은 데이터 자바로 변환한거


    @Data
    private static class ArrList{
        public String routeId;
        public String routeNo;
        public String moveDir;
        public int bsGap;
        public String bsNm;
        public String vhcNo2;
        public String busTCd2;
        public String busTCd3;
        public String busAreaCd;
        public String arrState;
        public int prevBsGap;
        public int arrTime;
    }
    @Data
    private static class Body{
        public ArrayList<Item> items;
        public int totalCount;
    }
    @Data
    private static class Header{
        public boolean success;
        public String resultCode;
        public String resultMsg;
    }
    @Data
    private static class Item{
        public String routeNo;
        public ArrayList<ArrList> arrList;
    }
    @Data
    private static class Root{
        public Header header;
        public Body body;
    }







}
