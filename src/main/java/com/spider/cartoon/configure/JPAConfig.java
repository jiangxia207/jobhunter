package com.spider.cartoon.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = {"com.spider.cartoon.dao"})
@EnableTransactionManagement(proxyTargetClass = true)
public class JPAConfig {
    
    
    
    
    
}