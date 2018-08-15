package com.spider.cartoon.configure;

import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.DataSourceProxy;
import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.alibaba.druid.sql.SQLUtils;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

public class CustomerFilter extends FilterEventAdapter{
    
    public static final Logger logger = LoggerFactory.getLogger(CustomerFilter.class);
    
    private DataSourceProxy dataSource;
    
    public void init(DataSourceProxy dataSource)
    {
        this.dataSource = dataSource;
    }
    
    @Override
    protected void statementExecuteBefore(StatementProxy statement, String sql)
    {
        //System.out.println("before sql time:"+ System.currentTimeMillis());
        getSqls(statement, sql);
    }

    @Override
    protected void statementExecuteQueryAfter(StatementProxy statement, String sql, ResultSetProxy result)
    {
        getSqls(statement, sql);
    }
    
    @Override
    protected void statementExecuteAfter(StatementProxy statement, String sql, boolean firstResult)
    {
        getSqls(statement, sql);
    }
    
    protected void statementExecuteUpdateAfter(StatementProxy statement, String sql, int updateCount)
    {
        getSqls(statement, sql);
    }
    
    private void getSqls(StatementProxy statement, String sql)
    {
        Map<Integer, JdbcParameter> pm = statement.getParameters();
        int size = 0;
        if((pm != null)&&(!CollectionUtils.isEmpty(pm.values())))
        {
            size = pm.size();
        }
        
        List<Object> a =  Lists.newArrayList();
        for(int i=0;i<size;i++)
        {
            a.add(pm.get(i).getValue());
        }
        //System.out.println("dbtype:"+dataSource.getDbType());
        logger.info(SQLUtils.format(sql, dataSource.getDbType(), a ));
        //System.out.println("before sql execute: "+ SQLUtils.format(sql, dataSource.getDbType(), a ) );
    }
}
