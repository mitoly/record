//package springboot.base.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration          // 配置文件
//@EnableWebSecurity      // 开启Security
//@EnableGlobalMethodSecurity(prePostEnabled = true)  //允许进入页面方法前检验 AOP
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//	//数组中定义的所有权限都能访问
////	private static final String[] exclusivePaths = {"/main/login", "/login"}; 
//	private static final String[] exclusivePaths = {"*"}; 
//	@Override
//    protected void configure(HttpSecurity http) throws Exception {
//
////        //路由策略和访问权限的简单配置
//        http
//        	.authorizeRequests()
//	        	.antMatchers(exclusivePaths)
//	        	.permitAll()
//	        	.anyRequest().authenticated()
//	        	.and()
//            .formLogin()                        //启用默认登陆页面 .loginPage("/main/login") //配置自定义登入页
//            	.failureUrl("/login?error")         //登陆失败返回URL:/login?error
//            	.defaultSuccessUrl("/home")         //登陆成功跳转URL
//            	.permitAll()		                 //登陆页面全部权限可访问
//        		.and()
//        	.logout()
//        		.permitAll();
//
//        super.configure(http);
//    }
//	
//	
//	
//	/**
//     * 配置内存用户
//     */
////    @Autowired
////    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////        auth
////            .inMemoryAuthentication()
////            .withUser("admin").password("1").roles("USER");
////    }
//
//	
//}
