package com.gallery.galleryui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.zkoss.zk.au.http.DHtmlUpdateServlet;
import org.zkoss.zk.ui.http.DHtmlLayoutServlet;
import org.zkoss.zk.ui.http.HttpSessionListener;

import java.util.HashMap;
import java.util.Map;


@EntityScan(basePackages = {"com.gallery.gallerymodel"})
@ComponentScan(basePackages = {"com.gallery"})
@EnableJpaRepositories(basePackages = {"com.gallery.repository"})
@EnableAutoConfiguration(exclude = {JpaRepositoriesAutoConfiguration.class})
@SpringBootApplication(scanBasePackages = {"com.gallery"})
public class GalleryUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GalleryUiApplication.class, args);
    }

    // ZK servlets
    @Bean
    public ServletRegistrationBean dHtmlLayoutServlet() {
        Map<String, String> params = new HashMap<>();
        params.put("update-uri", "/src/main/webapp/WEB-INF/zkau");
        ServletRegistrationBean reg = new ServletRegistrationBean(new DHtmlLayoutServlet(), "*.zul");
        reg.setLoadOnStartup(1);
        reg.setInitParameters(params);
        return reg;
    }

    @Bean
    public ServletRegistrationBean dHtmlUpdateServlet() {
        Map<String, String> params = new HashMap<>();
        params.put("update-uri", "/src/main/webapp/WEB-INF/zkau/*");
        ServletRegistrationBean reg = new ServletRegistrationBean(new DHtmlUpdateServlet(), "/src/main/webapp/WEB-INF/zkau/*");
        reg.setLoadOnStartup(2);
        reg.setInitParameters(params);
        return reg;
    }

    // ZK listeners
    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener> httpSessionListener() {
        return new ServletListenerRegistrationBean<>(new HttpSessionListener());
    }
}
