package cn.it.interceptor;

import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class UserConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        System.out.println("拦截aaaaaaaaaaaaaaaaa");
        registry.addInterceptor(new LoginInterceptor())
        .addPathPatterns("/admin/**")//拦截
        .excludePathPatterns("/admin")
        .excludePathPatterns("/admin/login");


    }
}
