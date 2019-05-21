package springboot.webconfig;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springboot.webservice.UserWebService;
import springboot.webservice.impl.UserWebServiceImpl;

import javax.xml.ws.Endpoint;


@Configuration
public class CxfConfig {

    // 方法名不能使用dispatchServlet，会覆盖默认的启动项
    @Bean
    public ServletRegistrationBean disServlet() {
        return new ServletRegistrationBean(new CXFServlet(),"/ws/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public UserWebService userWebService() {
        return new UserWebServiceImpl();
    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), userWebService());
        endpoint.publish("/userApi");
        return endpoint;
    }

}
