package springboot.masterdata.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.base.entity.QueryCriteria;
import springboot.base.service.impl.BaseServiceImpl;
import springboot.masterdata.role.dao.TrUserRoleDao;
import springboot.masterdata.role.dao.TsRoleDao;
import springboot.masterdata.role.entity.TrUserRole;
import springboot.masterdata.role.vo.RoleVo;
import springboot.masterdata.user.dao.TsUserDao;
import springboot.masterdata.user.entity.TsUser;
import springboot.masterdata.user.service.TsUserService;
import springboot.masterdata.user.vo.UserVo;
import springboot.util.ConstantUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TsUserServiceImpll extends BaseServiceImpl implements TsUserService {//, UserDetailsService{

	@Autowired
	private TsRoleDao roleDao;
	@Autowired
	private TsUserDao userDao;
	@Autowired
	private TrUserRoleDao trUserRoleDao;
	
//	@Override
//	public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
//		UserVo userVo = userDao.findUserByAccoun(account);
//		if(null == userVo){
//			throw new UsernameNotFoundException("用户名不对");
//		}
//		return userVo;
//	}

	@Override
	public List<RoleVo> findRoleByUserId(Integer userId) {
		return roleDao.findRolesByUserId(userId);
	}

	@Override
	public UserVo findUserById(Integer id) {
		return userDao.findUserById(id);
	}

	@Override
	public UserVo findUserByAccount(String account) {
		return userDao.findUserByAccoun(account);
	}

	@Override
	public PageInfo<TsUser> list(QueryCriteria queryCriteria, TsUser user) {
		PageHelper.startPage(queryCriteria.getCurrentPage(), queryCriteria.getPageSize());
		Example ex = new Example(TsUser.class);
		Example.Criteria criteria = ex.createCriteria();
		criteria.andCondition("MARK_FOR_DELETE=", "0"); //逻辑删除
		if(StringUtils.isNotBlank(user.getAccount())) {
			criteria.andLike("account", "%" + user.getAccount() + "%");
		}
		if(StringUtils.isNotBlank(user.getUserName())) {
			criteria.andLike("userName", "%" +user.getUserName() + "%");
		}
		ex.setOrderByClause("ID DESC"); //排序
		List<TsUser> userlist = userDao.selectByExample(ex);
		PageInfo<TsUser> pageInfo = new PageInfo<TsUser>(userlist);
		return pageInfo;
	}

	@Override
	public void save(TsUser user) {
		userDao.insert(user);
	}

	@Override
	public void update(TsUser user) {
		userDao.updateByPrimaryKey(user);
	}

	@Override
	public void remove(String ids, UserVo userVo) {
		userDao.remove(ids, userVo);
	}

	@Override
	public void addUser(HttpServletRequest request, HttpServletResponse response, TsUser user) {
		UserVo userVo = super.getCurrentUser(request, response); //当前登入人
		user.setMarkForDelete(ConstantUtils.ENABLED);
		user.setPassword("1"); //默认密码
		user.setIsEnabled(ConstantUtils.YES); //默认为启用
		user.setCreateTime(new Date());
		user.setCreateUser(userVo.getId());
		this.save(user);
	}

	@Override
	public void editUser(HttpServletRequest request, HttpServletResponse response, TsUser user) {
		UserVo currentUser = super.getCurrentUser(request, response);
		user.setUpdateTime(new Date());
		user.setUpdateUser(currentUser.getId());
		user.setOptCounter(null != user.getOptCounter()?user.getOptCounter()+1: 1); //修改次数统计
		this.update(user);
	}

	@Override
	public void savePermission(Integer userId, Integer[] saveRoleArr) {
		Example example = new Example(TrUserRole.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andCondition("USER_ID =", userId);
		trUserRoleDao.deleteByExample(example);
		if (saveRoleArr.length > 0) {
			List<TrUserRole> userRoleList = new ArrayList<TrUserRole>();
			for (Integer roleId : saveRoleArr){
				userRoleList.add(new TrUserRole(userId, roleId));
			}
			trUserRoleDao.insertList(userRoleList);
		}
	}

}
