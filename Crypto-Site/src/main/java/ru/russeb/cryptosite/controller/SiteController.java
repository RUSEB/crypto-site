package ru.russeb.cryptosite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//Контроллер начальной страницы
@Controller
public class SiteController {
    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/crypto")
    public String choice(){
        return "crypto";
    }

}
