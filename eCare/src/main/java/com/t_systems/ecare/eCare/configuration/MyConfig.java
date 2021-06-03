package com.t_systems.ecare.eCare.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.t_systems.ecare.eCare")
@EnableWebMvc
@EnableTransactionManagement
public class MyConfig extends WebMvcConfigurerAdapter {
    @Bean
    public ViewResolver viewResolver()
    {
        InternalResourceViewResolver internalResourceViewResolver=new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/WEB-INF/view/");
        internalResourceViewResolver.setSuffix(".jsp");

        return internalResourceViewResolver;
    }
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

    @Bean
    public DataSource dataSource()
    {
        ComboPooledDataSource dataSource=new ComboPooledDataSource();
        try {
            dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
            dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/ecare_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC");
            dataSource.setUser("bestuser");
            dataSource.setPassword("bestuser");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSource;
    }
    @Bean
    public LocalSessionFactoryBean sessionFactoryBean(){
        LocalSessionFactoryBean sessionFactory=new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.t_systems.ecare.eCare.entity");
        Properties hibernateProperties=new Properties();
        hibernateProperties.setProperty("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
        hibernateProperties.setProperty("hibernate.show_sql","true");
        hibernateProperties.setProperty("hibernate.connection.Charset","UTF-8");
        hibernateProperties.setProperty("hibernate.connection.CharacterEncoding","UTF-8");
        hibernateProperties.setProperty("hibernate.connection.Useunicode","true");
        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }
    @Bean
    public HibernateTransactionManager transactionManager()
    {
        HibernateTransactionManager transactionManager=new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactoryBean().getObject());
        return transactionManager;
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}
