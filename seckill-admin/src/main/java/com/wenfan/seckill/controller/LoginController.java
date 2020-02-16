package com.wenfan.seckill.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Created by wenfan on 2019/12/29 19:14
 */

@RestController
public class LoginController {

    @GetMapping("/")
    public RedirectView loginPage() {
        return new RedirectView("login.html");
    }

}
