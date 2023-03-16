package com.zyk.music.config;

import cn.hutool.extra.spring.SpringUtil;
import cn.org.atool.fluent.mybatis.spring.MapperFactory;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
//@MapperScan({"com.zyk.music.mapper"})
public class MapperConfig {
//    @Resource
//    DataSourceProperties properties;
//    @Bean("dataSource")
//    public DataSource newDataSource() {
//        // TODO
////        return SpringUtil.getBean("dataSource");
//        MysqlDataSource source = new MysqlDataSource();
//        source.setUrl(properties.getUrl());
//        source.setUser(properties.getUsername());
//        source.setPassword(properties.getPassword());
//        return source;
//    }
//
//    // 定义fluent mybatis的MapperFactory
//    @Bean
//    public SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(newDataSource());
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        bean.setMapperLocations(resolver.getResources("classpath*:mapper/*.xml"));
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
//        configuration.setLazyLoadingEnabled(true);
//        configuration.setMapUnderscoreToCamelCase(true);
//        bean.setConfiguration(configuration);
//        return bean;
//    }

//    @Bean
//    public MapperFactory mapperFactory() {
//        return new MapperFactory();
//    }
}
