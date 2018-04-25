package com.bigapple.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: lirenfei
 * @date: 2018/4/25
 */
@Controller
public class RootController {

    @RequestMapping("/")
    public Object root(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        return "/dist/index.html";
    }

}
