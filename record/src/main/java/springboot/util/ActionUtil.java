package springboot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import springboot.base.entity.JsonObjectResult;

/**
 * 后台像前台发送响应工具类
 * @author mitol
 *
 */
public class ActionUtil {

	private static final Logger log = LoggerFactory.getLogger(ActionUtil.class);
	
	/**
	 * 常规相应
	 * @return
	 */
	public static JsonObjectResult sendResult(){
		JsonObjectResult result = new JsonObjectResult();
		result.setSuccess(true);
		return result;
	}
	
	/**
	 * 带参相应
	 * @param object
	 * @return
	 */
	public static JsonObjectResult sendResult(Object object){
		JsonObjectResult result = new JsonObjectResult();
		result.setSuccess(true);
		result.setData(object);
		return result;
	}
	
	/**
	 * 带参相应
	 * @param message
	 * @param success
	 * @return
	 */
	public static JsonObjectResult sendResult(String message, boolean success){
		JsonObjectResult result = new JsonObjectResult();
		result.setSuccess(success);
		result.setMessage(message);
		return result;
	}

	/**
	 * 带参相应
	 * @param message
	 * @param object
	 * @param success
	 * @return
	 */
	public static JsonObjectResult sendResult(String message, Object object, boolean success){
		JsonObjectResult result = new JsonObjectResult();
		result.setSuccess(success);
		result.setMessage(message);
		result.setData(object);
		return result;
	}
}
