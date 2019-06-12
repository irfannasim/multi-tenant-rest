package com.rd.mtr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Date;
import java.util.Locale;


/**
 * @author Irfan Nasim
 * @description To create JAR Packaging
 * @since 19-Nov-2018
 */
@SpringBootApplication(scanBasePackages = {"com.rd.mtr"})
public class MultiTenantRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiTenantRestApplication.class, args);
        System.out.println("Multi-Tenant Application is up at " + new Date());
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.setBasename("messages");
        rs.setDefaultEncoding("UTF-8");
        rs.setUseCodeAsDefaultMessage(true);
        return rs;
    }
}

/**
 * @author Irfan Nasim
 * @description To create WAR Packaging
 * @since 19-Nov-2018
 */
/*@SpringBootApplication
public class MultiTenantApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MultiTenantApplication.class, args);
        System.out.println("MultiTenant Application is up at " + new Date());
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MultiTenantApplication.class);
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.setBasename("messages");
        rs.setDefaultEncoding("UTF-8");
        rs.setUseCodeAsDefaultMessage(true);
        return rs;
    }
}*/

