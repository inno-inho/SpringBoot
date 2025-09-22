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
import java.util.List;


// 대구광역시 돌발 교통정보 조회 서비스
@Controller
@Slf4j
@RequestMapping("/BusStop")
public class OpenData04Controller {

    private String server = "https://apis.data.go.kr/1613000/BusSttnInfoInqireService/getSttnThrghRouteList";      // 공공데이터의 엔드포인트 잡아서 설정하기
//  https://apis.data.go.kr/1613000/BusSttnInfoInqireService/getSttnThrghRouteList?serviceKey=3bf653831dbac6f912c7cf62b9fc65dd9b5f6992dc7c29c327f2432b05901b1f&pageNo=1&numOfRows=10&_type=xml&cityCode=25&nodeid=DJB8002536 이었음
    private String serviceKey = "3bf653831dbac6f912c7cf62b9fc65dd9b5f6992dc7c29c327f2432b05901b1f";     // 공공데이터의 일반 인증키
    private String pageNo;
    private String numOfRows;
    private String _type;
    private String cityCode;
    private String nodeid;

    @GetMapping(value = "/{pageNo}/{numOfRows}/{_type}/{cityCode}/{nodeid}")
    public String get(
        @PathVariable(value = "pageNo", required = true) String pageNo,
        @PathVariable(value = "numOfRows", required = true) String numOfRows,
        @PathVariable(value = "_type", required = true) String _type,
        @PathVariable(value = "cityCode", required = true) String cityCode,
        @PathVariable(value = "nodeid", required = true) String nodeid,

        Model model
    )
    {
        log.info("GET // Incident ...pageno: " + pageNo + ", Amount: " +numOfRows);
        this.pageNo = pageNo;
        this.numOfRows = numOfRows;
        this._type = _type;
        this.cityCode = cityCode;
        this.nodeid = nodeid;



        // 파라미터 설정(service key 포함)
//        String url = UriComponentsBuilder.fromHttpUrl(server)       // 얘가 URL에 적히는 파라미터 값 대신함
//                .queryParam("serviceKey", "serviceKey" )              // 왜인지 이거는 안됬음
//                .queryParam("pageNo", pageNo)
//                .queryParam("numOfRows", numOfRows)
//                .toUriString();

        String url = server;        // 얘가 URL에 적히는 파라미터 값 대신함
        url+= "?serviceKey=" + serviceKey;
        url+="&pageNo=" + pageNo;
        url+="&numOfRows="+numOfRows;
        url+="&_type="+_type;
        url+="&cityCode="+cityCode;
        url+="&nodeid="+nodeid;


        RestTemplate restTemplate = new RestTemplate();     // 요청 준비


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

        System.out.println(response.getBody());

        // Rest Type을 clsss type으로 반환

        Root root = response.getBody();
        Response resp = root.getResponse();
        Body body = resp.getBody();
        Items items = body.getItems();

        List<Item> list = items.getItem();
        list.forEach((item) -> {
            System.out.println("item");
        });

        model.addAttribute("list", list);

        return "Opendata/index4";




//        Root root = response.getBody();
//        Body body = root.getBody();
//        Items items = body.getItems();
//        List<Item> list = items.getItem();
//
//        list.forEach(System.out::println);
//
//        model.addAttribute("list", list);
//        return "Opendata/index1";

    }


//        json으로 받은 데이터 자바로 변환한거
    @Data
    private static class Body{
        public Items items;
        public int numOfRows;
        public int pageNo;
        public int totalCount;
    }
    @Data
    private static class Header{
        public String resultCode;
        public String resultMsg;
    }
    @Data
    private static class Item{
        public String endnodenm;
        public String routeid;
        public int routeno;
        public String routetp;
        public String startnodenm;
    }
    @Data
    private static class Items{
        public ArrayList<Item> item;
    }
    @Data
    private static class Response{
        public Header header;
        public Body body;
    }
    @Data
    private static class Root{
        public Response response;
    }





}
