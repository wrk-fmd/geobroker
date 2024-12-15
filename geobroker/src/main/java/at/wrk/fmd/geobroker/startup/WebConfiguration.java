/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.startup;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    private static final Logger LOG = LoggerFactory.getLogger(WebConfiguration.class);

    private final Gson gson;
    private final String allowedOrigins;

    @Autowired
    public WebConfiguration(final Gson gson, @Value("${startup.initial.configuration:*}") final String allowedOrigins) {
        this.gson = gson;
        this.allowedOrigins = allowedOrigins;
    }

    @Override
    public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
        GsonHttpMessageConverter httpMessageConverter = new GsonHttpMessageConverter();
        httpMessageConverter.setGson(gson);
        converters.add(httpMessageConverter);
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        LOG.info("Configuring CORS with allowed origins: '{}'", allowedOrigins);
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}
