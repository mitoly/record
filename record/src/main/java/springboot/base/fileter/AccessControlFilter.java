package springboot.base.fileter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccessControlFilter implements javax.servlet.Filter{

	public static final String[] ALLOW_DOMAIN  = new String[]{"http://localhost:8080"};

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterchain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String originHeader = request.getHeader("Origin");
		if(Arrays.asList(ALLOW_DOMAIN).contains(originHeader)){
			System.out.println("###跨域过滤器->当前访问来源->"+originHeader+"###");
			//跨域设置  如果要做细的限制，仅限某域名下的可以进行跨域访问到此，可以将*改为对应的域名。
			response.setHeader("Access-Control-Allow-Origin", originHeader); //originHeader
			response.setHeader("Access-Control-Allow-Methods", "*");
			response.setHeader("Access-Control-Max-Age", "1728000");
			response.setHeader("Access-Control-Allow-Credentials", "true");
			response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		}
        filterchain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
