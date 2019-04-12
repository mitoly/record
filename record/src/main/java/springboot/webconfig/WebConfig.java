package springboot.webconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springboot.base.interceptor.CheckLoginInterceptor;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

    @Autowired
    private CheckLoginInterceptor checkLoginInterceptor;

    /**
     * 配置拦截器，需要继承WebMvcConfigurerAdapter
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkLoginInterceptor)
        	.excludePathPatterns("/user/login");
    }

}
