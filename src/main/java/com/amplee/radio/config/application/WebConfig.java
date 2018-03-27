package com.amplee.radio.config.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableAspectJAutoProxy
@EnableWebMvc
@ComponentScan({"com.amplee.radio.*"})
@PropertySource("classpath:/cred.prop")
public class WebConfig extends WebMvcConfigurerAdapter {
    private final
    Environment     env;

    @Autowired
    public WebConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setContentType("text/html; charset=utf-8");
        return viewResolver;
    }

    @Bean(name="encoder")
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/users");
        driverManagerDataSource.setUsername(env.getProperty("usr"));
        driverManagerDataSource.setPassword(env.getProperty("pwd"));
        return driverManagerDataSource;
    }

    @Bean
    public CustomerService customerService() {
        return new CustomerService();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/views/css/");
        registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/views/images/");
        registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/views/js/");
        registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/views/resources/");
        registry.addResourceHandler("/robots.txt").addResourceLocations("/WEB-INF/robots.txt");
        registry.addResourceHandler("/.well-known/acme-challenge/**")
                .addResourceLocations("/WEB-INF/lets/");
    }
}
