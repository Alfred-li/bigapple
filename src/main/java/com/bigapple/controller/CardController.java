package com.bigapple.controller;

import com.bigapple.model.CardInfo;
import com.bigapple.service.CardServcie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/card/")
public class CardController {

    @Autowired
    private CardServcie cardServcie;

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public String verify(@RequestParam("cardNo") String cardNo,
                         @RequestParam("password") String password) {
        log.info(" cardNo:{}, password:{}. ", cardNo, password);
        String token = "aaa";
        final boolean flag = cardServcie.verifyCard(cardNo, password, token);
        return flag+"";
    }

    @RequestMapping(value = "/fill_info", method = RequestMethod.POST)
    public Object fillInfo(@RequestParam("id") long id,
                           @RequestParam("address") String address,
                           @RequestParam("reportDate") long date) {
        log.info(" fill info, id:{}, address:{}, reportDate:{}. ", id, address, date);
        String token = "aaa";
        final boolean flag = cardServcie.fillUserInfo(token, date, id, address);
        return flag+"";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object getCardInfo(@PathVariable("id") long id) {
        log.info(" get card info by id:{}. ", id);
        final CardInfo cardInfo = cardServcie.selectCardInfo(id);
        return cardInfo;
    }

}