package springboot.masterdata.menu.service;

import springboot.base.service.BaseService;
import springboot.masterdata.menu.entity.TsMenu;

import java.util.List;
import java.util.Map;

public interface TsMenuService extends BaseService {

    /**
     * 通过Id查询表单,不传为查出全部
     * @param ids 表单ID数组
     * @return
     */
    public List<TsMenu> findMenuByIds(Integer[] ids);

    /**
     * 查询表单数据，各参数选填
     * @param menuId 表单ID
     * @param menuName 表单名称
     * @param menuCode 表单编码
     * @param permissionCode 表单位置编码
     * @param parentCode 父类位置编码
     * @return
     */
    List<TsMenu> findMenuByParam(Integer menuId, String menuName, String menuCode, String permissionCode, String parentCode);

    /**
     * 通过角色Id查找所关联的菜单
     * @param onlyParent 是否只查找父类
     * @param roleId
     * @param parentCode 子类查找父类Code
     * @return
     */
    List<TsMenu> findMenus(boolean onlyParent, Integer roleId, String parentCode);

    /**
     * 通过onlyParent 和 parentCode 查询表单，通过roleId来查看哪些表单被设置权限，查询结果checkRole有值则说明被该角色再该表单设置权限
     * @param onlyParent 是否只查找父级菜单
     * @param roleId 检查选中状态角色ID
     * @param parentCode 父级Code
     * @return
     */
    List<Map<String, Object>> findMenusCheckType(boolean onlyParent, Integer roleId, String parentCode);

    /**
     * 可以尝试通过permissionTable 查询出菜单中的权限按钮所有。checkRole有值哪些是选中的
     * @param permissionTable 表中的permission_table字段 记录了属于哪个菜单
     * @param permissionCode  表中的permission_button字段 记录了属于哪个类型
     * @return
     */
    List<Map<String, Object>> findPermissionButtonCheckType(String permissionTable, String permissionCode, String roleId);

    /**
     * 可以尝试通过permissionTable 查询出菜单中的权限按钮所有。checkRole有值哪些是选中的
     * @param permissionTable 表中的permission_table字段 记录了属于哪个菜单
     * @param permissionCode  表中的permission_button字段 记录了属于哪个类型
     * @return
     */
    List<Map<String, Object>> findPermissionTableCheckType(String permissionTable, String permissionCode, String roleId);
}
