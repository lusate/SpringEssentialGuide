package com.example.springessentialguide.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class MetaDBConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource-meta") // application.yml에 있는 변수 설정값을 자동으로 불러올 수 있는 애노테이션
    public DataSource metaDBSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary // DB 충돌 방지
    @Bean
    public PlatformTransactionManager metaTransactionManager() {
        return new DataSourceTransactionManager(metaDBSource());
    }
}
