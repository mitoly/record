package springboot.base.entity;

import java.io.Serializable;

public class JsonObjectResult implements Serializable{
	
	private static final long serialVersionUID = -5848191257519754406L;
	
	private boolean success;
	private String message;
	private Object data;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
