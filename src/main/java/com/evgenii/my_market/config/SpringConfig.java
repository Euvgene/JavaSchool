package com.evgenii.my_market.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Locale;
import java.util.Properties;

/**
 * Web configuration class.
 *
 * @author Boznyakov Evgenii
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("com.evgenii.my_market")
@PropertySource({"classpath:application.properties"})
@RequiredArgsConstructor
public class SpringConfig implements WebMvcConfigurer {
    /**
     * Creates an instance of this class using constructor-based dependency injection.
     */
    private final Environment env;

    /**
     * Configures {@linkplain org.springframework.web.servlet.ViewResolver ViewResolver} for .jsp
     * pages.
     *
     * @return configured instance of {@linkplain org.springframework.web.servlet.ViewResolver
     * ViewResolver}
     */
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    /**
     * Add a resource handler for serving static resources
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/");
    }

    /**
     * Configures {@linkplain com.mchange.v2.c3p0.ComboPooledDataSource} for data source
     *
     * @return configured instance of
     * {@linkplain com.mchange.v2.c3p0.ComboPooledDataSource}
     */
    @Bean
    public DataSource myDataSource() {

        ComboPooledDataSource myDataSource = new ComboPooledDataSource();

        try {
            myDataSource.setDriverClass("com.mysql.jdbc.Driver");
        } catch (PropertyVetoException exc) {
            exc.printStackTrace();
        }

        myDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        myDataSource.setUser(env.getProperty("jdbc.user"));
        myDataSource.setPassword(env.getProperty("jdbc.password"));

        myDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
        myDataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
        myDataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
        myDataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));

        return myDataSource;
    }

    private int getIntProperty(String propName) {

        String propVal = env.getProperty(propName);

        assert propVal != null;
        return Integer.parseInt(propVal);
    }

    /**
     * Configures {@linkplain org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean}
     *
     * @return configured instance of {@linkplain org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean}
     */
    @Bean
    @DependsOn("flyway")
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

    /**
     * Configures {@linkplain org.springframework.orm.jpa.JpaTransactionManager}
     *
     * @return configured instance of {@linkplain  org.springframework.orm.jpa.JpaTransactionManager}
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty("hibernate.show_sql", "false");

        return properties;
    }

    /**
     * Configures {@linkplain org.springframework.web.servlet.i18n.SessionLocaleResolver}
     * set default language us
     *
     * @return configured instance of {@linkplain  org.springframework.web.servlet.i18n.SessionLocaleResolver}
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    /**
     * Configure {@linkplain org.springframework.web.servlet.i18n.LocaleChangeInterceptor}
     * set param name
     *
     * @return configured instance of {@linkplain org.springframework.web.servlet.i18n.LocaleChangeInterceptor}
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    /**
     * Configure {@linkplain org.springframework.context.support.ReloadableResourceBundleMessageSource}
     * Set a single basename, following the basic ResourceBundle convention of not specifying file extension or language codes.
     * Set the default charset to use for parsing properties files.
     *
     * @return {@linkplain org.springframework.context.support.ReloadableResourceBundleMessageSource} singleton instance
     */
    @Bean(name = "messageSource")
    public MessageSource getMessageResource() {
        ReloadableResourceBundleMessageSource messageResource = new ReloadableResourceBundleMessageSource();
        messageResource.setBasename("classpath:messages");
        messageResource.setDefaultEncoding("UTF-8");
        return messageResource;
    }

    /**
     * Configure {@linkplain org.flywaydb.core.Flyway}
     * Set location of script file and data source
     *
     * @return configured instance of {@linkplain org.flywaydb.core.Flyway}
     */
    @Bean(initMethod = "migrate")
    Flyway flyway() {
        Flyway flyway = new Flyway();
        flyway.setBaselineOnMigrate(true);
        flyway.setLocations("db/migration");
        flyway.setDataSource("jdbc:mysql://localhost:3306/my_store", "root", "root");
        flyway.migrate();
        return flyway;
    }
}
