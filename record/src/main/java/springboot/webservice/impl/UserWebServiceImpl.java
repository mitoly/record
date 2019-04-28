package springboot.webservice.impl;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import springboot.masterdata.user.entity.TsUser;
import springboot.masterdata.user.service.TsUserService;
import springboot.webservice.UserWebService;

import javax.jws.WebService;

@WebService(serviceName = "UserWebService", targetNamespace = "http://webservice.springboot", // 一般为包名倒叙
        endpointInterface = "springboot.webservice.UserWebService") // webService接口
public class UserWebServiceImpl implements UserWebService {

    @Autowired
    private TsUserService userService;

    @Override
    public TsUser getUser(String account) {
        TsUser user = userService.findUserByAccount(account);
        return user;
    }

}
