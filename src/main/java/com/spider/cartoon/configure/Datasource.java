package com.spider.cartoon.configure;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;


@Configuration
public class Datasource {
    
    @Value(value = "${spring.datasource.url}")
    private String url;
    @Value(value = "${spring.datasource.driverClassName}")
    private String driverClassName;
    @Value(value = "${spring.datasource.username}")
    private String userName;
    @Value(value = "${spring.datasource.password}")
    private String password;
    
    
    
    @Bean
    public DataSource DataSource() {
        
        DruidDataSource datasource = new DruidDataSource();
        List<String> initsqls = Lists.newArrayList();
        initsqls.add("set names utf8mb4;");
        datasource.setConnectionInitSqls(initsqls);
        datasource.setDriverClassName(driverClassName);
        datasource.setUrl(url);
        datasource.setUsername(userName);
        datasource.setPassword(password);

        datasource.setInitialSize(3);
        datasource.setMinIdle(3);
        datasource.setMaxActive(50);
        datasource.setMaxWait(50000);
        datasource.setTimeBetweenEvictionRunsMillis(120000);
        datasource.setMinEvictableIdleTimeMillis(600000);
        datasource.setValidationQuery("select 1 from dual");
        datasource.setValidationQueryTimeout(3000);
        datasource.setTestWhileIdle(true);
        datasource.setTestOnBorrow(true);
        datasource.setTestOnReturn(true);

        try {
            datasource.setFilters("stat,log4j");
        } catch (SQLException e) {
            throw new RuntimeException("data source failed");
        }


        List<Filter> filters = new ArrayList<Filter>();
        filters.add(log4jFilter());
        filters.add(new CustomerFilter());
        datasource.setProxyFilters(filters);

        return datasource;
    }
    
    private Log4jFilter log4jFilter() {
        Log4jFilter bean = new Log4jFilter();
        bean.setStatementExecutableSqlLogEnable(true);
        bean.setStatementCreateAfterLogEnabled(false);
        bean.setStatementPrepareAfterLogEnabled(false);
        bean.setStatementPrepareCallAfterLogEnabled(false);
        bean.setStatementExecuteAfterLogEnabled(false);
        bean.setStatementExecuteQueryAfterLogEnabled(false);
        bean.setStatementExecuteUpdateAfterLogEnabled(false);
        bean.setStatementExecuteBatchAfterLogEnabled(false);
        bean.setStatementCloseAfterLogEnabled(false);
        bean.setStatementParameterClearLogEnable(false);
        bean.setStatementParameterSetLogEnabled(false);
        return bean;
    }

}
