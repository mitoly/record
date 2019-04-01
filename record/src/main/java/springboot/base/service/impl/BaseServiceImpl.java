package springboot.base.service.impl;

import org.springframework.transaction.annotation.Transactional;
import springboot.base.service.BaseService;
import springboot.masterdata.user.vo.UserVo;
import springboot.util.ConstantUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Transactional()
public class BaseServiceImpl implements BaseService {
    public UserVo getCurrentUser(HttpServletRequest request, HttpServletResponse response){
        return (UserVo) request.getSession().getAttribute(ConstantUtils.CURRENT_USER);
    }
}
