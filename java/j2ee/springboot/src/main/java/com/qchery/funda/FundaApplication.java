package com.qchery.funda;

import com.qchery.funda.jwt.JwtFilter;
import com.qchery.funda.props.ApplicationProperties;
import com.qchery.funda.props.SystemProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

/**
 * @author Chery
 * @date 2017/3/24 - 下午11:12
 */
@SpringBootApplication
@EnableConfigurationProperties({
        SystemProperties.class
})
public class FundaApplication {

    private static final Logger logger = LoggerFactory.getLogger(FundaApplication.class);
    @Autowired
    private ApplicationProperties properties;

    public static void main(String[] args) {
        SpringApplication.run(FundaApplication.class, args);
    }

    @PostConstruct
    private void init(){
        logger.info("Spring Boot - @ConfigurationProperties annotation example");
        logger.info(properties.toString());
    }

    //过滤器
    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }

}
