package springboot.base.service.impl;

import springboot.base.service.BaseService;
import springboot.masterdata.user.vo.UserVo;
import springboot.util.ConstantUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServiceImpl implements BaseService {
    public UserVo getCurrentUser(HttpServletRequest request, HttpServletResponse response){
        return (UserVo) request.getSession().getAttribute(ConstantUtils.CURRENT_USER);
    }
}
