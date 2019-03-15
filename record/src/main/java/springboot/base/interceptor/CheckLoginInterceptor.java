package springboot.base.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import springboot.util.ConstantUtils;

@Component
public class CheckLoginInterceptor implements HandlerInterceptor{

	private final Logger log = LoggerFactory.getLogger(CheckLoginInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("用户访问" + request.getRequestURI());
		HttpSession session = request.getSession();
		if(null == session.getAttribute(ConstantUtils.CURRENT_USER)){
			log.info("用户访问Session过期:" + request.getRequestURI());
			throw new Exception("过期");
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
