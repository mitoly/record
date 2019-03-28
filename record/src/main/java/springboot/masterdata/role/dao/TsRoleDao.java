package springboot.masterdata.role.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import springboot.base.dao.BaseMapper;
import springboot.masterdata.role.entity.TsRole;
import springboot.masterdata.role.vo.RoleVo;
import springboot.masterdata.user.vo.UserVo;

import java.util.List;
import java.util.Map;

@Mapper
public interface TsRoleDao extends BaseMapper<TsRole> {

	/**
	 * 通过用户Id查询角色
	 *
	 * @param userId 用户Id
	 * @return
	 */
	@Select("SELECT t.* "
			+ "FROM "
			+ "	TS_ROLE t RIGHT JOIN TR_USER_ROLE tur "
			+ "ON "
			+ "	tur.ROLE_ID = t.ID "
			+ "WHERE "
			+ "	tur.USER_ID = #{userId}")
	@Results({
			@Result(property = "id", column = "ID"),
			@Result(property = "menus", column = "ID",
					many = @Many(select = "springboot.masterdata.menu.dao.TsMenuDao.findMenusByRoleId")),
			@Result(property = "permissionButtons", column = "ID",
					many = @Many(select = "springboot.masterdata.menu.dao.TsMenuDao.findPermissionButtonByRoleId")),
			@Result(property = "permissionTables", column = "ID",
					many = @Many(select = "springboot.masterdata.menu.dao.TsMenuDao.findPermissionTableByRoleId"))
	})
	List<RoleVo> findRolesByUserId(Integer userId);

	@Select("SELECT t.* "
			+ "FROM "
			+ "	TS_ROLE t "
			+ "WHERE "
			+ "	t.ID = #{id}")
	@Results({
			@Result(property = "id", column = "ID"),
			@Result(property = "menus", column = "ID",
					many = @Many(select = "springboot.masterdata.menu.dao.TsMenuDao.findMenusByRoleId"))
	})
	RoleVo findRolesByRoleId(Integer id);

	/**
	 * 通过角色代码查询角色
	 *
	 * @param roleCode 角色代码
	 * @return
	 */
	@Select("SELECT t.* "
			+ "FROM "
			+ "	TS_ROLE t "
			+ "WHERE "
			+ "	t.ROLE_CODE = #{roleCode}")
	@Results({
			@Result(property = "id", column = "ID"),
			@Result(property = "menus", column = "ID",
					many = @Many(select = "springboot.masterdata.role.dao.TsMenuDao.findMenusByRoleId"))
	})
	List<RoleVo> findRolesByRoleCode(String roleCode);

	@Update("UPDATE TS_ROLE t " +
			"SET " +
			"	t.MARK_FOR_DELETE = '1', " +
			"	 t.UPDATE_TIME = SYSDATE(), " +
			" 	t.UPDATE_USER = #{userVo.id} " +
			"WHERE " +
			"	t.ID IN (${ids}) ")
	void remove(@Param("ids") String ids, @Param("userVo") UserVo userVo);

	@SelectProvider(type = RoleDaoProvider.class, method = "findRoleCheckType")
    List<Map<String, Object>> findRoleCheckType(Integer userId);

	class RoleDaoProvider {
		public String findRoleCheckType (Integer userId) {
			return new SQL(){
				{
					SELECT("tr.ID, tr.ROLE_CODE, tr.ROLE_NAME, t.ROLE_ID AS CHECKROLE");
					FROM("ts_role tr");
					LEFT_OUTER_JOIN("(SELECT * " +
							"	FROM tr_user_role tur " +
							" 	where tur.user_id = " + userId + ") t " +
							" ON tr.ID = t.ROLE_ID ");
					WHERE(" tr.MARK_FOR_DELETE = '0' ");
				}
			}.toString();
		}

	}
}