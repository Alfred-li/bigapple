package com.bigapple.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.yaoqianshu.data.dao.busi"}, sqlSessionFactoryRef = "busiSqlSessionFactory")
public class BusiDBConfiguration {

    static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

    @Resource(name = "busiDataSource")
    private DataSource busiDatasource;

    @Primary
    @Bean(name = "busiTransactionManager")
    public DataSourceTransactionManager busiTransactionManager() {
        return new DataSourceTransactionManager(busiDatasource);
    }

    @Primary
    @Bean(name = "busiSqlSessionFactory")
    public SqlSessionFactory busiSqlSessionFactory()
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(busiDatasource);
        /*sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MAPPER_LOCATION));*/
        return sessionFactory.getObject();
    }
}