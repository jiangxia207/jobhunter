package com.spider.cartoon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.spider.cartoon.common.CommonConstants.SourceType;
import com.spider.cartoon.repository.Cartoon;

@Component
public interface CartoonDao  extends JpaRepository<Cartoon, String>{
    
    
    Cartoon findByIdAndSourceType(String id, SourceType SourceType);

}
