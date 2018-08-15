package com.spider.cartoon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spider.cartoon.common.CommonConstants.SourceType;
import com.spider.cartoon.dao.CartoonDao;
import com.spider.cartoon.repository.Cartoon;

@Service
public class CartoonService {

    @Autowired
    private CartoonDao cartoonDao;

    
    
    @Transactional
    public Cartoon saveIfNotExist(Cartoon cartoon)
    {
        Cartoon cartoonDb = cartoonDao.findByIdAndSourceType(cartoon.getId(), SourceType.mg);
        if(cartoonDb != null)
        {
            return null;
        }
        cartoonDao.save(cartoon);
        return cartoon;
    }
    
        
    
}
