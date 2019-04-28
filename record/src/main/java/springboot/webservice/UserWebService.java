package springboot.webservice;

import springboot.masterdata.user.entity.TsUser;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "UserWebService")
public interface UserWebService {

    @WebMethod
    TsUser getUser(@WebParam(name="account")String account); // 为了避免发布后参数名为arg0 需要加上@WebParam

}
