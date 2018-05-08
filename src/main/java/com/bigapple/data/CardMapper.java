package com.bigapple.data;

import com.bigapple.model.CardInfo;
import com.bigapple.model.Dao;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;

/**
 * @author: lirenfei
 * @date: 2018/4/23
 */
public interface CardMapper {

    /**
     * 验证卡信息
     * @param cardNo
     * @param cardPassword
     * @return
     */
    @Select(" SELECT id FROM A WHERE card_no = #{no} AND card_password = #{password} AND action = 0 AND card_state = 0 ")
    Long selectUnusedCard(@Param("no") String cardNo, @Param("password") String cardPassword);

    /**
     * 获取卡明细信息
     * @param cardID
     * @return
     */
    @Select(" SELECT card.id as id, type.card_title as title, type.card_description as description FROM A as card LEFT JOIN B as type ON card.card_type_id = type.ID " +
            " WHERE card.id = #{cardID} " +
            " AND card.action = 0" +
            " AND type.ACTION = 0 ")
    @Results({
            @Result(property = "id",  column = "id"),
            @Result(property = "cardTitle", column = "title"),
            @Result(property = "cardDescription", column = "description")
    })
    CardInfo selectCardDetail(@Param("cardID") long cardID);

    /**
     * 用户和卡关系表入库
     * @param dao
     * @return
     */
    @Insert(" INSERT INTO C (card_id, token) VALUES (#{dao.cardID}, #{dao.token}) ")
    @Options(useGeneratedKeys=true, keyProperty="dao.id", keyColumn="id")
    int insertCardUserRelation(@Param("dao") Dao dao);

    /**
     * 更新卡的状态
     * @param cardID
     * @return
     */
    @Update(" UPDATE A SET card_state = 1 WHERE id = #{cardID} AND action = 0 AND card_state = 0 ")
    int updateCardStatus(@Param("cardID") long cardID);

    /**
     * 更新卡的状态
     * @param cardID
     * @return
     */
    @Update(" UPDATE A SET card_state = 2 WHERE id = #{cardID} AND action = 0 AND card_state = 0 ")
    int updateCardStatusUserComplete(@Param("cardID") long cardID);

    /**
     * 更新用户信息
     * @return
     */
    @Update(" UPDATE C SET phone=#{phone}, name = #{name}, address = #{address}, except_date = #{date} WHERE id = #{id} AND token = #{token} AND action = 0 ")
    int updateUserInfo(@Param("phone") String phone, @Param("name") String name, @Param("date") Timestamp timestamp, @Param("token") String token, @Param("id") long id, @Param("address") String address);

    /**
     * 更新卡的完成状态
     * @return
     */
    @Update(" UPDATE A SET card_state = 2 WHERE id IN (SELECT card_id FROM C WHERE id = #{id} ) ")
    int updateCardStateComplete(@Param("id") long id);


}
