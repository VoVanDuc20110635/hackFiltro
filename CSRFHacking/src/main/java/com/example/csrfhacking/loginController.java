package com.example.csrfhacking;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/nhungquanbanhngon")
    public String comment(){return "comment"; }

    @GetMapping("/nhungquanbanhngon2")
    public String comment2(){return "comment2"; }

    @GetMapping("/nhungquancaphengon")
    public String clickjacking(){return "clickJacking"; }

    @GetMapping("/nhungquancaphengon2")
    public String clickjacking2(){return "clickJacking2"; }
}
