package springboot.masterdata.menu.dao;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import springboot.base.dao.BaseMapper;
import springboot.masterdata.menu.entity.TsMenu;
import springboot.masterdata.menu.entity.TsPermissionButton;
import springboot.masterdata.menu.entity.TsPermissionTable;

import java.util.List;
import java.util.Map;

@Mapper
public interface TsMenuDao extends BaseMapper<TsMenu> {

	@SelectProvider(type = MenuDaoProvider.class, method = "findMenuByIds")
	List<TsMenu> findMenuByIds(@Param("ids")Integer[] ids);

	@SelectProvider(type = MenuDaoProvider.class, method = "findMenuByPermissionCodes")
	List<TsMenu> findMenuByPermissionCodes(@Param("findMenuByPermissionCodes")String[] findMenuByPermissionCodes);

	@SelectProvider(type = MenuDaoProvider.class, method = "findMenuByParam")
	List<TsMenu> findMenuByParam(Integer menuId, String menuName, String menuCode, String permissionCode, String parentCode);

	/**
	 * 查询角色关联表单
	 * @param onlyParent 是否只查找父级菜单
	 * @param roleId 角色ID
	 * @param parentCode 父级Code
	 * @return
	 */
	@SelectProvider(type = MenuDaoProvider.class, method = "findMenus")
	List<TsMenu> findMenus(boolean onlyParent,Integer roleId, String parentCode);

	/**
	 * 通过onlyParent 和 parentCode 查询表单，通过roleId来查看哪些表单被设置权限，查询结果checkRole有值则说明被该角色设置权限
	 * @param onlyParent 是否只查找父级菜单
	 * @param roleId 角色ID
	 * @param parentCode 父级Code
	 * @return
	 */
	@SelectProvider(type = MenuDaoProvider.class, method = "findMenusCheckType")
	List<Map<String, Object>> findMenusCheckType(boolean onlyParent, Integer roleId, String parentCode);

	@Select("SELECT t.* "
			+ "FROM "
			+ "	TS_MENU t RIGHT JOIN TR_ROLE_MENU trm "
			+ "ON "
			+ "	trm.MENU_ID = t.ID "
			+ "WHERE "
			+ "	trm.ROLE_ID = #{roleId}")
	List<TsMenu> findMenusByRoleId(Integer roleId);

	/**
	 * 删除tr_role_menu表中的权限
	 * @param roleId 角色Id
	 * @param menuIds 表单id 如果为空，则删除所有该角色的权限
	 */
	@DeleteProvider(type = MenuDaoProvider.class, method ="deleteRoleMenu")
	void deleteRoleMenu(@Param("roleId")Integer roleId, @Param("menuIds")Integer[] menuIds);

	@SelectProvider(type = MenuDaoProvider.class, method = "findPermissionTableByParam")
	List<TsPermissionTable> findPermissionTableByParam(Integer id, String permissionTable, String permissionCode, String permissionName);

	@Select("SELECT t.* " +
			"FROM " +
			"	TS_PERMISSION_TABLE t RIGHT JOIN TR_ROLE_PERMISSION_TABLE trpt " +
			"ON " +
			"	t.id = trpt.permission_table_id " +
			"WHERE " +
			"	trpt.role_id = #{roleId}")
	List<TsPermissionTable> findPermissionTableByRoleId(Integer roleId);

	/**
	 * 可以尝试通过permissionTable 查询出菜单中的权限按钮所有。checkRole有值哪些是选中的
	 * @param permissionTable 表中的permission_table字段 记录了属于哪个菜单
	 * @param permissionCode  表中的permission_button字段 记录了属于哪个类型
	 * @return
	 */
	@SelectProvider(type = MenuDaoProvider.class, method = "findPermissionTableCheckType")
	List<Map<String, Object>> findPermissionTableCheckType(String permissionTable, String permissionCode, String roleId);

	/**
	 * 删除tr_permission_table表中的权限
	 * @param roleId 角色Id
	 * @param tableIds 表单id 如果为空，则删除所有该角色的权限
	 */
	@DeleteProvider(type = MenuDaoProvider.class, method ="deleteRolePermissionTable")
	void deleteRolePermissionTable(@Param("roleId")Integer roleId, @Param("tableIds")Integer[] tableIds);




	@Select("SELECT b.* " +
			"FROM " +
			"	TS_PERMISSION_BUTTON b RIGHT JOIN TR_ROLE_PERMISSION_BUTTON trpb " +
			"ON " +
			"	b.id = trpb.permission_button_id " +
			"WHERE " +
			"	trpb.role_id = #{roleId}")
	List<TsPermissionButton> findPermissionButtonByRoleId(Integer roleId);

	/**
	 * 可以尝试通过permissionTable 查询出菜单中的权限按钮所有。checkRole有值哪些是选中的
	 * @param permissionTable 表中的permission_table字段 记录了属于哪个菜单
	 * @param permissionCode  表中的permission_button字段 记录了属于哪个类型
	 * @return
	 */
	@SelectProvider(type = MenuDaoProvider.class, method = "findPermissionButtonCheckType")
	List<Map<String, Object>> findPermissionButtonCheckType(String permissionTable, String permissionCode, String roleId);

	@SelectProvider(type = MenuDaoProvider.class, method = "findPermissionButtonByParam")
	List<TsPermissionButton> findPermissionButtonByParam(Integer id, String permissionTable, String permissionCode, String permissionName);

	/**
	 * 删除tr_permission_button表中的权限
	 * @param roleId 角色Id
	 * @param buttonIds 表单id 如果为空，则删除所有该角色的权限
	 */
	@DeleteProvider(type = MenuDaoProvider.class, method ="deleteRolePermissionButton")
	void deleteRolePermissionButton(@Param("roleId")Integer roleId,@Param("buttonIds")Integer[] buttonIds);


	class MenuDaoProvider{
		public String findMenuByIds(Integer [] ids){
			return new SQL() {
				{
					SELECT("t.*");
					FROM("TS_MENU t");
					if (null != ids && ids.length > 0) {
						StringBuffer idSql = new StringBuffer();
						for(int i=0,size=ids.length; i<size; i++){
							idSql.append(ids[i]);
							if(i<size-1){
								idSql.append(",");
							}
						}
						WHERE("t.ID IN (" + idSql + ")");
					}
				}
			}.toString();
		}

		public String findMenuByPermissionCodes(String[] findMenuByPermissionCodes){
			return new SQL() {
				{
					SELECT("t.*");
					FROM("TS_MENU t");
					if (null != findMenuByPermissionCodes && findMenuByPermissionCodes.length > 0) {
						StringBuffer codeSql = new StringBuffer();
						for(int i=0,size=findMenuByPermissionCodes.length; i<size; i++){
							codeSql.append(findMenuByPermissionCodes[i]);
							if(i<size-1){
								codeSql.append(",");
							}
						}
						WHERE("t.PERMISSION_CODE IN (" + codeSql + ")");
					}
				}
			}.toString();
		}

		public String findMenuByParam(Integer menuId, String menuName, String menuCode, String permissionCode, String parentCode){
			return new SQL() {
				{
					SELECT("t.*");
					FROM("TS_MENU t");
					if (null != menuId) {
						WHERE("t.ID = " + menuId);
					}
					if (StringUtils.isNotBlank(menuName)) {
						WHERE("NAME = '" + menuName + "'");
					}
					if (StringUtils.isNotBlank(menuCode)) {
						WHERE("CODE = '" + menuCode + "'");
					}
					if (StringUtils.isNotBlank(permissionCode)) {
						WHERE("PERMISSION_CODE = '" + permissionCode + "'");
					}
					if (StringUtils.isNotBlank(parentCode)) {
						WHERE("PARENT_CODE = '" + parentCode + "'");
					}
				}
			}.toString();
		}

		public String findMenus(boolean onlyParent, Integer roleId, String parentCode){
			return new SQL(){
				{
					SELECT("t.*");
					FROM("TS_MENU t");
					RIGHT_OUTER_JOIN("TR_ROLE_MENU trm ON trm.MENU_ID = t.ID");
					if (onlyParent) {
						WHERE("t.PARENT_CODE is not null ");
					}
					if (null != roleId) {
						WHERE("trm.ROLE_ID = " + roleId);
					}
					if (StringUtils.isNotBlank(parentCode)){
						WHERE("t.PARENT_CODE = " + parentCode);
					}

				}
			}.toString();
		}

		public String findMenusCheckType(boolean onlyParent, Integer roleId, String parentCode){
			return new SQL(){
				{
					SELECT("t.* , e.role_id as CHECKROLE");
					FROM("TS_MENU t");
					LEFT_OUTER_JOIN("(SELECT " +
							"			* " +
							"		FROM " +
							"			TS_MENU t2 RIGHT JOIN tr_role_menu trm ON trm.MENU_ID = t2.ID " +
							"		WHERE " +
							"			1 = 1 " + (null!=roleId?"AND trm.ROLE_ID = "+roleId:"") + ") e " +
							"	ON t.ID = e.ID");
					if (onlyParent) {
						WHERE("t.PARENT_CODE is null ");
					}
					if (StringUtils.isNotBlank(parentCode)){
						WHERE("t.PARENT_CODE = " + parentCode);
					}
					ORDER_BY("t.PERMISSION_CODE ASC");
				}
			}.toString();
		}

		public String findPermissionButtonCheckType(String permissionTable, String permissionCode, String roleId){
			return new SQL(){
				{
					SELECT("t.* , e.role_id as CHECKROLE");
					FROM("TS_PERMISSION_BUTTON t");
					LEFT_OUTER_JOIN("(SELECT " +
							"			* " +
							"		FROM " +
							"			TS_PERMISSION_BUTTON t2 RIGHT JOIN TR_ROLE_PERMISSION_BUTTON rpb ON rpb.PERMISSION_BUTTON_ID = t2.ID " +
							"		WHERE " +
							"			1 = 1 " + (null!=roleId?"AND rpb.ROLE_ID = "+roleId:"") +
							"		) e " +
							"	ON t.ID = e.ID");
					if (StringUtils.isNotBlank(permissionTable)){
						WHERE("t.PERMISSION_TABLE = '" + permissionTable +"'");
					}
					if (StringUtils.isNotBlank(permissionCode)){
						WHERE("t.PERMISSION_CODE = '" + permissionCode +"'");
					}
					ORDER_BY("t.ID ASC");
				}
			}.toString();
		}

		public String findPermissionTableCheckType(String permissionTable, String permissionCode, String roleId){
			return new SQL(){
				{
					SELECT("t.* , e.role_id as CHECKROLE");
					FROM("TS_PERMISSION_TABLE t");
					LEFT_OUTER_JOIN("(SELECT " +
							"			* " +
							"		FROM " +
							"			TS_PERMISSION_TABLE t2 RIGHT JOIN TR_ROLE_PERMISSION_TABLE rpt ON rpt.PERMISSION_TABLE_ID = t2.ID " +
							"		WHERE " +
							"			1 = 1 " + (null!=roleId?"AND rpt.ROLE_ID = "+roleId:"") +
							"		) e " +
							"	ON t.ID = e.ID");
					if (StringUtils.isNotBlank(permissionTable)){
						WHERE("t.PERMISSION_TABLE = '" + permissionTable +"'");
					}
					if (StringUtils.isNotBlank(permissionCode)){
						WHERE("t.PERMISSION_CODE = '" + permissionCode +"'");
					}
					ORDER_BY("t.ID ASC");
				}
			}.toString();
		}

		public String deleteRoleMenu(Integer roleId, Integer[] menuIds){
			return new SQL(){
				{
					DELETE_FROM("TR_ROLE_MENU");
					if (null != roleId) {
						WHERE("ROLE_ID = " + roleId);
					}
					if (null != menuIds && menuIds.length > 0) {
						StringBuffer sbSql = new StringBuffer();
						for (int i=0, size=menuIds.length; i<size; i++){
							sbSql.append(menuIds[i]);
							if (i < size-1) {
								sbSql.append(",");
							}
						}
						WHERE("MENU_ID IN {"+ sbSql +")");
					}
				}
			}.toString();
		}

		public String findPermissionTableByParam(Integer id, String permissionTable, String permissionCode, String permissionName) {
			return new SQL(){
				{
					SELECT("t.*");
					FROM("TS_PERMISSION_TABLE t");
					if (null != id) {
						WHERE("t.ID = " + id);
					}
					if (StringUtils.isNotBlank(permissionTable)) {
						WHERE("t.PERMISSION_TABLE = '" + permissionTable + "'");
					}
					if (StringUtils.isNotBlank(permissionCode)) {
						WHERE("t.PERMISSION_CODE = '" + permissionCode + "'");
					}
					if (StringUtils.isNotBlank(permissionName)) {
						WHERE("t.PERMISSION_NAME = '" + permissionName + "'");
					}
				}
			}.toString();
		}

		public String findPermissionButtonByParam(Integer id, String permissionTable, String permissionCode, String permissionName) {
			return new SQL(){
				{
					SELECT("t.*");
					FROM("TS_PERMISSION_BUTTON t");
					if (null != id) {
						WHERE("t.ID = " + id);
					}
					if (StringUtils.isNotBlank(permissionTable)) {
						WHERE("t.PERMISSION_TABLE = '" + permissionTable + "'");
					}
					if (StringUtils.isNotBlank(permissionCode)) {
						WHERE("t.PERMISSION_CODE = '" + permissionCode + "'");
					}
					if (StringUtils.isNotBlank(permissionName)) {
						WHERE("t.PERMISSION_NAME = '" + permissionName + "'");
					}
				}
			}.toString();
		}

		public String deleteRolePermissionTable (Integer roleId, Integer[] tableIds) {
			return new SQL() {
				{
					DELETE_FROM("TR_ROLE_PERMISSION_TABLE");
					if (null != roleId) {
						WHERE("ROLE_ID = " + roleId);
					}
					if (null != tableIds && tableIds.length > 0 ) {
						StringBuffer sbSql = new StringBuffer();
						for (int i=0, size=tableIds.length; i<size; i++){
							sbSql.append(tableIds[i]);
							if (i < size-1) {
								sbSql.append(",");
							}
						}
						WHERE("PERMISSION_TABLE_ID IN ("+ sbSql +")");
					}
				}
			}.toString();
		}

		public String deleteRolePermissionButton (Integer roleId, Integer[] buttonIds) {
			return new SQL() {
				{
					DELETE_FROM("TR_ROLE_PERMISSION_BUTTON");
					if (null != roleId) {
						WHERE("ROLE_ID = " + roleId);
					}
					if (null != buttonIds && buttonIds.length > 0 ) {
						StringBuffer sbSql = new StringBuffer();
						for (int i=0, size=buttonIds.length; i<size; i++){
							sbSql.append(buttonIds[i]);
							if (i < size-1) {
								sbSql.append(",");
							}
						}
						WHERE("PERMISSION_BUTTON_ID IN ("+ sbSql +")");
					}
				}
			}.toString();
		}
	}
}