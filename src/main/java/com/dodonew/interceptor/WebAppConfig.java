package com.dodonew.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Bruce on 2017/10/16.
 * @Configuration注解，一定要加上，这样才能识别是要加配置，才可以识别出要添加的拦截器。
 */
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DataSecurityInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
