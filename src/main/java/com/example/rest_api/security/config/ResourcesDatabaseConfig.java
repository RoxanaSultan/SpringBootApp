package com.example.rest_api.security.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.rest_api.database.resources.repository",
        entityManagerFactoryRef = "resourcesEntityManagerFactory",
        transactionManagerRef = "resourcesTransactionManager"
)
public class ResourcesDatabaseConfig {
    @Bean(name = "resourcesDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.resources")
    public DataSource resourcesDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "resourcesEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean resourcesEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("resourcesDataSource") DataSource resourcesDataSource) {
        return builder
                .dataSource(resourcesDataSource)
                .packages("com.example.rest_api.database.resources.model") // Modelele pentru resources
                .persistenceUnit("resources")
                .build();
    }

    @Bean(name = "resourcesTransactionManager")
    public PlatformTransactionManager resourcesTransactionManager(
            @Qualifier("resourcesEntityManagerFactory") EntityManagerFactory resourcesEntityManagerFactory) {
        return new JpaTransactionManager(resourcesEntityManagerFactory);
    }
}