package com.bigapple.controller;

import com.bigapple.data.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    @Autowired
    private TestMapper dao;

    @RequestMapping("/")
    public String index() {
        final int chartCount = dao.getChartCount();
        return "chart counts: " + chartCount;
    }

}