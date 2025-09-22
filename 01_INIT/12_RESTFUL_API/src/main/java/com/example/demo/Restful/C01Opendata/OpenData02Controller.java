//package com.example.demo.Restful.C01Opendata;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//
//@Controller
//@Slf4j
//@RequestMapping("/WEATHER")
//public class OpenData02Controller {
//
////    getUltraSrtNcst 초단기날씨 실황조회
//    private String server = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";      // 공공데이터의 엔드포인트 잡아서 설정하기
////  https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?serviceKey=3bf653831dbac6f912c7cf62b9fc65dd9b5f6992dc7c29c327f2432b05901b1f&pageNo=1&numOfRows=1000&dataType=JSON&base_date=20250918&base_time=1500&nx=89&ny=90
//    private String serviceKey = "3bf653831dbac6f912c7cf62b9fc65dd9b5f6992dc7c29c327f2432b05901b1f";     // 공공데이터의 일반 인증키
//    private String pageNo;
//    private String numOfRows;
//    private String dataType;
//    private String base_date;
//    private String base_time;
//    private String nx;
//    private String ny;
//
//
//
//    @GetMapping(value = "/{pageNo}/{numOfRows}/{dataType}/{base_date}/{base_time}/{nx}/{ny}")
//    public String get(
//        @PathVariable(value = "pageNo", required = true) String pageNo,
//        @PathVariable(value = "numOfRows", required = true) String numOfRows,
//        @PathVariable(value = "dataType", required = true) String dataType,
//        @PathVariable(value = "base_date", required = true) String base_date,
//        @PathVariable(value = "base_time", required = true) String base_time,
//        @PathVariable(value = "nx", required = true) String nx,
//        @PathVariable(value = "ny", required = true) String ny,
//        Model model
//    )
//    {
//        log.info("GET // Weather ...pageno: {} numOfRows: {} dataType: {} base_date: {} base_time: {} nx = {} ny = {}", pageNo, numOfRows, dataType, base_date, base_time, nx, ny);
//        this.pageNo = pageNo;
//        this.numOfRows = numOfRows;
//        this.dataType = dataType;
//        this.base_date = base_date;
//        this.base_time = base_time;
//        this.nx = nx;
//        this.ny = ny;
//
//
//
//        // 파라미터 설정(service key 포함)
////        String url = UriComponentsBuilder.fromHttpUrl(server)       // 얘가 URL에 적히는 파라미터 값 대신함
////                .queryParam("serviceKey", "serviceKey" )              // 왜인지 이거는 안됬음
////                .queryParam("pageNo", pageNo)
////                .queryParam("numOfRows", numOfRows)
////                .toUriString();
//
//        String url = server;        // 얘가 URL에 적히는 파라미터 값 대신함
//        url+= "?serviceKey=" + serviceKey;
//        url+="&pageNo=" + pageNo;
//        url+="&numOfRows=" + numOfRows;
//        url+="&dataType="+dataType;
//        url+="&base_date="+base_date;
//        url+="&base_time="+base_time;
//        url+="&nx="+nx;
//        url+="&ny="+ny;
//
//
////
//        RestTemplate restTemplate = new RestTemplate();     // 요청 준비
//
//
////                // 요청 헤더 설정(x)  (api에 맞춰야함)
////        HttpHeaders header = new HttpHeaders();
////        header.add("key","value");
////        // 요청 바디 설정(x)
////        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
////        params.add("","");
////
////        HttpEntity< MultiValueMap<String,String>  > entity = new HttpEntity<>(params,header);
//
//
//
//            // 요청 후 응답 확인
//            ResponseEntity<Root> response = restTemplate.exchange(url, HttpMethod.GET, null, Root.class);
//
//
//            //        Rest Type을 clsss type으로 반환
//            System.out.println(response.getBody());
////
////
////
////
//            Root root = response.getBody();
//            Response resp = root.getResponse();
//            Body body = resp.getBody();
//
//            Items items = body.getItems();
//
//            List<Item> list = items.getItem();
//            list.forEach(System.out::println);
//
//
//        model.addAttribute("list", list);
//        return "Opendata/index2";
////
//    }
////
////
////
//
//
//    @Data
//    private static class Body{
//        public String dataType;
//        public Items items;
//        public int pageNo;
//        public int numOfRows;
//        public int totalCount;
//    }
//
//    @Data
//    private static class Header{
//        public String resultCode;
//        public String resultMsg;
//    }
//
//    @Data
//    private static class Item{
//        public String baseDate;
//        public String baseTime;
//        public String category;
//        public int nx;
//        public int ny;
//        public String obsrValue;
//    }
//
//    @Data
//    private static class Items{
//        public ArrayList<Item> item;
//    }
//
//    @Data
//    private static class Response{
//        public Header header;
//        public Body body;
//    }
//
//    @Data
//    private static class Root{
//        public Response response;
//    }
//
//
//
//
//
//
//}
