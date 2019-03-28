package springboot.masterdata.role.service;

import com.github.pagehelper.PageInfo;
import springboot.base.entity.QueryCriteria;
import springboot.base.service.BaseService;
import springboot.masterdata.role.entity.TsRole;
import springboot.masterdata.role.vo.RoleVo;
import springboot.masterdata.user.vo.UserVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


public interface TsRoleService extends BaseService {
    PageInfo<TsRole> list(QueryCriteria queryCriteria, TsRole role);

    RoleVo findRolesByRoleId(Integer id);

    /**
     * 通过角色代码查询角色
     * @param roleCode 角色
     * @return
     */
    RoleVo findRolesByRoleCode(String roleCode);

    void addRole(HttpServletRequest request, HttpServletResponse response, TsRole role);

    void editRole(HttpServletRequest request, HttpServletResponse response, TsRole role);

    void remove(String ids, UserVo currentUser);

    void savePermission(Integer roleId, String permissionCode, Integer[] menuIdsArr, Integer[] saveButtonIdsArr, Integer[] saveTableIdsArr);

    /**
     * 查询出所有角色，通过字段checkType是否有值来判断改用户是否选择该角色
     * @param userId 用户Id
     * @return
     */
    List<Map<String, Object>> findRoleCheckType(Integer userId);
}
