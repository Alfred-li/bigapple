package com.bigapple.data;

import org.apache.ibatis.annotations.Select;

/**
 * @author: lirenfei
 * @date: 2018/4/23
 */
public interface TestMapper {

    @Select(" SELECT COUNT(1) FROM tbl_chart ")
    int getChartCount();

}
