package com.evgenii.my_market.configs;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("com.evgenii.my_market")
@PropertySource({"classpath:application.properties"})
@RequiredArgsConstructor
public class SpringConfig implements WebMvcConfigurer {

    private final Environment env;

    // define a bean for ViewResolver
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/");
    }

    @Bean
    public DataSource myDataSource() {

        // create connection pool
        ComboPooledDataSource myDataSource = new ComboPooledDataSource();

        // set the jdbc driver
        try {
            myDataSource.setDriverClass("com.mysql.jdbc.Driver");
        }
        catch (PropertyVetoException exc) {
            throw new RuntimeException(exc);
        }

        // set database connection props
        myDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        myDataSource.setUser(env.getProperty("jdbc.user"));
        myDataSource.setPassword(env.getProperty("jdbc.password"));

        // set connection pool props
        myDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
        myDataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
        myDataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
        myDataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));

        return myDataSource;
    }

    private Properties getHibernateProperties() {

        // set hibernate properties
        Properties props = new Properties();

        props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));

        return props;
    }


    // need a helper method
    // read environment property and convert to int

    private int getIntProperty(String propName) {

        String propVal = env.getProperty(propName);

        // now convert to int

        assert propVal != null;
        return Integer.parseInt(propVal);
    }

    /*@Bean
    public LocalSessionFactoryBean sessionFactory(){

        // create session factorys
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        // set the properties
        sessionFactory.setDataSource(myDataSource());
        sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
        sessionFactory.setHibernateProperties(getHibernateProperties());

        return sessionFactory;
    }*/

    /*@Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

        // setup transaction manager based on session factory
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }*/

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(myDataSource());
        em.setPackagesToScan("com.evgenii.my_market.entity");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    /*@Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/my_store");
        dataSource.setUsername( "root" );
        dataSource.setPassword( "root" );
        return dataSource;
    }*/


    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

  /*  @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }*/

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty("hibernate.show_sql", "true");

        return properties;
    }

}
