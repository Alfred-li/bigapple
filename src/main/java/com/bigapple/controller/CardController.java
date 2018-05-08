package com.bigapple.controller;

import com.bigapple.model.CardInfo;
import com.bigapple.model.request.InfoRequest;
import com.bigapple.model.request.VerifyRequest;
import com.bigapple.model.response.Msg;
import com.bigapple.service.CardServcie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/")
public class CardController {

    @Autowired
    private CardServcie cardServcie;

    @RequestMapping(value = "/card/verify", method = RequestMethod.POST)
    public Object verify(@RequestBody VerifyRequest request) {
        log.info(" cardNo:{}, password:{}. ", request.getCardNo(), request.getPassword());
        String token = "aaa";
        int i = cardServcie.verifyCard(request.getCardNo(), request.getPassword(), token);
        if (i > 0) {
            Msg msg = new Msg();
            msg.setCode(200);

            Msg.ID id = new Msg.ID();
            id.setId(i);
            msg.setData(id);
            return msg;
        } else {
            Msg msg = new Msg();
            msg.setCode(500);
            msg.setMsg("卡号密码输入有错误");
            return msg;
        }
    }

    @RequestMapping(value = "/fill_info", method = RequestMethod.POST)
    public Object fillInfo(@RequestBody InfoRequest request) {
        log.info(" fill info, id:{}, address:{}, reportDate:{}. ", request);
        String token = "aaa";
        final boolean flag = cardServcie.fillUserInfo(token, request.getReportDate(), request.getId(), request.getAddress(), request.getName(), request.getPhone());
        if (flag) {
            Msg msg = new Msg();
            msg.setCode(200);
            msg.setMsg("成功");
            return msg;
        }
        Msg msg = new Msg();
        msg.setCode(500);
        msg.setMsg("失败");
        return msg;
    }

    @RequestMapping(value = "/card/{id}", method = RequestMethod.GET)
    public Object getCardInfo(@PathVariable("id") long id) {
        log.info(" get card info by id:{}. ", id);
        final CardInfo cardInfo = cardServcie.selectCardInfo(id);

        Msg msg = new Msg();
        msg.setCode(200);
        msg.setData(cardInfo);
        return msg;
    }

}