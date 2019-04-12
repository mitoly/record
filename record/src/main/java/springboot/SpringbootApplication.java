package springboot;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.cache.annotation.EnableCaching;

import springboot.base.fileter.AccessControlFilter;

/**
 * springboot启动类
 * @author mitol
 *
 */
@EnableCaching // 开启高速缓存，具体功能百度
@SpringBootApplication //等同于 @Configuration @EnableAutoConfiguration @ComponentScanpublic
public class SpringbootApplication implements ServletContextInitializer {

    public void onStartup(ServletContext servletContext) throws ServletException {
        // 配置Servlet
//        servletContext.addServlet("servletTest",new ServletDemo())
//                      .addMapping("/servletTest");
        // 配置Filter
        servletContext.addFilter("accessControlFilter",new AccessControlFilter())
                      .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");
        // 配置Listener
//        servletContext.addListener(new ListenerDemo());
    }
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
}