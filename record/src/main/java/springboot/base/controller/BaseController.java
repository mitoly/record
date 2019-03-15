package springboot.base.controller;

import springboot.masterdata.user.vo.UserVo;
import springboot.util.ConstantUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseController {

    public UserVo getCurrentUser(HttpServletRequest request, HttpServletResponse response){
        return (UserVo) request.getSession().getAttribute(ConstantUtils.CURRENT_USER);
    }

}
