package com.example.springessentialguide.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories( // basePackages의 경우 DataDBConfig가 어떤 패키지에 대해서 동작하는지 세팅이 필요합니
        basePackages = "com.example.springessentialguide.repository",
        entityManagerFactoryRef = "dataEntityManager", // 메서드명을 지정
        transactionManagerRef = "dataTransactionManager"
)
public class DataDBConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource-data")
    public DataSource dataDBSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 엔티티를 관리할 매니저
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean dataEntityManager() {
        // JPA를 사용하기 위해 엔티티 매니저 팩토리 빈을 생성
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        // 데이터베이스 소스를 설정
        // dataEntityManager() 메서드는 데이터베이스 연결을 위한 데이터 소스를 반환해야 합니다.
        em.setDataSource(dataDBSource());

        // 엔티티들이 모여질 패키지 등록 -> JPA가 엔티티 클래스를 찾을 패키지를 지정합니다.
        em.setPackagesToScan(new String[]{"com.example.springessentialguide.entity"});

        // JPA의 구현체로 Hibernate를 사용하도록 설정, HibernateJpaVendorAdapter는 Hibernate에 특화된 설정을 제공
        em. setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update"); // 데이터베이스 스키마를 관리
        properties.put("hibernate.show_sql", "true"); // SQL 쿼리를 콘솔에 출력하도록 설정
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public PlatformTransactionManager dataTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(dataEntityManager().getObject());

        return transactionManager;
    }
}
