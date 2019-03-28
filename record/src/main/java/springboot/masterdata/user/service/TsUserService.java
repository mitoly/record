package springboot.masterdata.user.service;

import com.github.pagehelper.PageInfo;
import springboot.base.entity.QueryCriteria;
import springboot.masterdata.role.vo.RoleVo;
import springboot.masterdata.user.entity.TsUser;
import springboot.masterdata.user.vo.UserVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface TsUserService {

	List<RoleVo> findRoleByUserId(Integer userId);

	UserVo findUserById(Integer id);

	UserVo findUserByAccount(String account);

	/**
	 * 列表查询
	 * @param queryCriteria
	 * @param user 查询条件
	 * @return
	 */
	PageInfo<TsUser> list(QueryCriteria queryCriteria, TsUser user);

	void save(TsUser user);

	void update(TsUser user);

	void remove(String ids, UserVo userVo);

	void addUser(HttpServletRequest request, HttpServletResponse response, TsUser user);

	void editUser(HttpServletRequest request, HttpServletResponse response, TsUser user);

    void savePermission(Integer userId, Integer[] saveRoleArr);
}
