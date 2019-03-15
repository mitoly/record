package springboot.base.service;

import springboot.masterdata.user.vo.UserVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BaseService {

    UserVo getCurrentUser(HttpServletRequest request, HttpServletResponse response);
}
