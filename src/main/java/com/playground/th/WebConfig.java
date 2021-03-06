package com.playground.th;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/image/*")
                .addResourceLocations("classpath:/static/image/")
                .setCachePeriod(20);
        registry.addResourceHandler("/upload/image/**")
                .addResourceLocations("classpath:/static/image/")
                .setCachePeriod(20);
    }
}
