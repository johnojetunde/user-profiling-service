package com.iddera.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = "com.iddera",
                       entityManagerFactoryRef = "idderaEntityManagerFactory",
                       transactionManagerRef = "idderaTransactionManager")
public class DataConfig {

    @Value("${spring.datasource.maxConnections}")
    int maxPoolSize;

    @Value("${spring.datasource.driverClassName}")
    String dbDriverClassName;

    @Value("${spring.datasource.url}")
    String datasourceUrl;

    @Value("${spring.datasource.username}")
    String datasourceUsername;

    @Value("${spring.datasource.password}")
    String datasourcePassword;

    @Value("${spring.datasource.jpa.dialect}")
    String hibernateDialect;

    @Value("${spring.datasource.liquibase.change-log}")
    String liquibaseChangeLogPath;

    @Value("${spring.datasource.liquibase.enabled}")
    Boolean liquibaseEnabled;

    @Value("${spring.datasource.jpa.hibernate.ddl-auto}")
    private String hbmDdl;

    @Bean(name = {"dataSource"})
    @Primary
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(dbDriverClassName);
        config.setJdbcUrl(datasourceUrl);
        config.setUsername(datasourceUsername);
        config.setPassword(datasourcePassword);
        config.setMaximumPoolSize(maxPoolSize);
        config.setConnectionTestQuery("SELECT 1");
        config.setPoolName("springHikariCP");
        config.addDataSourceProperty("dataSource.cachePrepStmts", "true");
        config.addDataSourceProperty("dataSource.prepStmtCacheSize", "250");
        config.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("dataSource.useServerPrepStmts", "true");
        return new HikariDataSource(config);
    }

    @Primary
    @Bean(name = "idderaEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean idderaEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        Map<String, String> jpaPropertyMap = new HashMap<>();
        jpaPropertyMap.put("hibernate.id.new_generator_mappings", "false");
        jpaPropertyMap.put("hibernate.dialect", hibernateDialect);
        jpaPropertyMap.put("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        jpaPropertyMap.put("hibernate.hbm2ddl.auto", hbmDdl);
        return builder
                .dataSource(dataSource())
                .packages(
                        "com.iddera"
                )
                .properties(jpaPropertyMap)
                .build();
    }

    @Primary
    @Bean(name = "idderaTransactionManager")
    public PlatformTransactionManager idderaTransactionManager(
            final @Qualifier("idderaEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }

    @Bean
    @Primary
    public SpringLiquibase liquibase(@Qualifier("dataSource") DataSource dSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dSource);
        liquibase.setChangeLog(liquibaseChangeLogPath);
        liquibase.setShouldRun(liquibaseEnabled);
        return liquibase;
    }
}
