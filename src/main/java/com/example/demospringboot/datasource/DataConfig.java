package com.example.demospringboot.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 *
 */
@Configuration
//@EnableTransactionManagement
@MapperScan(basePackages = "com.example.demospringboot.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class DataConfig {
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    @Primary
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:/mapper/*.xml"));
        return sessionFactoryBean.getObject();
    }

    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager setTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
