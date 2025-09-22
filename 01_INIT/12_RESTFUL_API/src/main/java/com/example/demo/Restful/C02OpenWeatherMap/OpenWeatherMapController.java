package com.example.demo.Restful.C02OpenWeatherMap;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

@RestController
@Slf4j
@RequestMapping("/OPEN_WEATHER")
public class OpenWeatherMapController {

    private String server = "https://api.openweathermap.org/data/2.5/weather";      // API데이터의 엔드포인트 잡아서 설정하기
    //   https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key} 이었음
    private String appid = "b7a263e63bfe790ff0081e9b619e7c91";     // API데이터의 일반 인증키


    @GetMapping("/{lat}/{lon}")
    public void get(
            @PathVariable("lat") String lat,
            @PathVariable("lon") String lon,
            Model model
    ) throws UnsupportedEncodingException {
        log.info("GET /OPEN_WEATHER...");

//         파라미터 설정(service key 포함)
        String url = UriComponentsBuilder.fromHttpUrl(server)       // 얘가 URL에 적히는 파라미터 값 대신함
                .queryParam("appid", appid, URLEncoder.encode(appid, "UTF-8"))              // 왜인지 이거는 안됬음
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .toUriString();
        System.out.println(url);
        // 요청 헤더 X
        // 요청 바디 X

        RestTemplate rt = new RestTemplate();
        ResponseEntity<Root> response = rt.exchange(url, HttpMethod.GET, null, Root.class);

        System.out.println(response.getBody());




    }




//    ==================================
//    JsonToJava
//    ==================================


    @Data
    private static class Clouds{
        public int all;
    }
    @Data
    private static class Coord{
        public double lon;
        public double lat;
    }
    @Data
    private static class Main{
        public double temp;
        public double feels_like;
        public double temp_min;
        public double temp_max;
        public int pressure;
        public int humidity;
        public int sea_level;
        public int grnd_level;
    }
    @Data
    private static class Root{
        public Coord coord;
        public ArrayList<Weather> weather;
        public String base;
        public Main main;
        public int visibility;
        public Wind wind;
        public Clouds clouds;
        public int dt;
        public Sys sys;
        public int timezone;
        public int id;
        public String name;
        public int cod;
    }
    @Data
    private static class Sys{
        public int type;
        public int id;
        public String country;
        public int sunrise;
        public int sunset;
    }
    @Data
    private static class Weather{
        public int id;
        public String main;
        public String description;
        public String icon;
    }
    @Data
    private static class Wind{
        public double speed;
        public int deg;
    }


}
