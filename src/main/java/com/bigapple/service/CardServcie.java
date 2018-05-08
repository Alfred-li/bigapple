package com.bigapple.service;

import com.bigapple.data.CardMapper;
import com.bigapple.model.CardInfo;
import com.bigapple.model.Dao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.Timestamp;

/**
 * @author: lirenfei
 * @date: 2018/4/23
 */
@Slf4j
@Service
public class CardServcie {

    @Autowired
    private CardMapper cardDao;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Transactional
    public int verifyCard(String cardNo, String password, String token) {
        log.info(" cardNo:{}, password:{} , token:{}.", cardNo, password, token);
        final Long cardID = cardDao.selectUnusedCard(cardNo, password);
        if (cardID == null) {
            log.info(" cardNo:{} verify failed. ", cardNo);
            return -1;
        }

        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setName("CopyApp");
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(defaultTransactionDefinition);

        int id = 0;
        try {
            Dao dao = new Dao();
            dao.setCardID(cardID);
            dao.setToken(token);
            cardDao.insertCardUserRelation(dao);
            id = dao.getId();
            if (id <= 0) {
                log.info(" insert failed, cardID:{}, token:{}. ", cardID, token);
                transactionManager.rollback(status);
                return -1;
            }

            final int num1 = cardDao.updateCardStatus(cardID);
            if (num1 <= 0) {
                log.info(" update card status failed, cardID:{}. ", cardID);
                transactionManager.rollback(status);
                return -1;
            }
        } catch (Exception e) {
            log.info(" error, e:{}. ", e);
        }

        log.info(" cardNo:{} verify success. ", cardNo);
        transactionManager.commit(status);
        return id;
    }


    @Transactional
    public boolean fillUserInfo(String token, long time, long id, String address, String name, String phone) {
        log.info("token:{}, time:{}, id:{}.", token, time, id);
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setName("CopyApp");
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(defaultTransactionDefinition);

        final int num = cardDao.updateUserInfo(phone, name, new Timestamp(time), token, id, address);

        final int num1 = cardDao.updateCardStateComplete(id);

        transactionManager.commit(status);
        return true;
    }

    public CardInfo selectCardInfo(Long cardID) {
        log.info(" select cardinfo by id:{} ", cardID);
        final CardInfo cardInfo = cardDao.selectCardDetail(cardID);
        return cardInfo;
    }

}
