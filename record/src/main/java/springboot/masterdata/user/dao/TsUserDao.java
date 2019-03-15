package springboot.masterdata.user.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import springboot.base.dao.BaseMapper;
import springboot.masterdata.user.entity.TsUser;
import springboot.masterdata.user.vo.UserVo;

@Mapper
public interface TsUserDao extends BaseMapper<TsUser>{

	@Select("SELECT * FROM TS_USER WHERE ACCOUNT = #{account} AND MARK_FOR_DELETE = '0'")
	@Results({
		@Result(property = "id", column = "ID"),
		@Result(property = "roles", column = "ID",
				many = @Many(select = "springboot.masterdata.role.dao.TsRoleDao.findRolesByUserId"))
	})
	UserVo findUserByAccoun(String account);

	@Select("SELECT * FROM TS_USER WHERE ID = #{id}")
	@Results({
			@Result(property = "id", column = "ID"),
			@Result(property = "roles", column = "ID",
					many = @Many(select = "springboot.masterdata.role.dao.TsRoleDao.findRolesByUserId"))
	})
	UserVo findUserById(Integer id);

	@UpdateProvider(type = UserDaoProvider.class, method = "removeUser")
	void remove(String ids, UserVo userVo);

	class UserDaoProvider{

		public String removeUser(String ids, UserVo userVo){
			return new SQL(){
				{
					UPDATE("TS_USER");
					SET("MARK_FOR_DELETE = '1'");
					SET("UPDATE_TIME = SYSDATE()");
					SET("UPDATE_USER = " + userVo.getId());
					WHERE("ID IN (" + ids + ")");
				}
			}.toString();
		}

	}

}