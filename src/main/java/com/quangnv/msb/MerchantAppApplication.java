package com.quangnv.msb;

import com.quangnv.msb.configuration.beanFactory.BaseRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.quangnv.msb.repository"},
        repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class
)
@EntityScan(basePackages = "com.quangnv.msb.entity")
@SpringBootApplication
public class MerchantAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MerchantAppApplication.class, args);
    }

}
