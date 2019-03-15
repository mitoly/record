package springboot.masterdata.role.controller;

import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springboot.base.controller.BaseController;
import springboot.base.entity.JsonObjectResult;
import springboot.base.entity.QueryCriteria;
import springboot.masterdata.menu.entity.TsMenu;
import springboot.masterdata.menu.service.TsMenuService;
import springboot.masterdata.role.entity.TsRole;
import springboot.masterdata.role.service.TsRoleService;
import springboot.masterdata.role.vo.RoleVo;
import springboot.masterdata.user.vo.UserVo;
import springboot.util.ActionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/role")
@RestController
public class TsRoleController extends BaseController {

    @Autowired
    private TsRoleService roleService;
    @Autowired
    private TsMenuService menuService;

    @GetMapping("/list")
    public JsonObjectResult list(QueryCriteria queryCriteria, TsRole role){
        PageInfo<TsRole> rolePageInfo = roleService.list(queryCriteria, role);
        return ActionUtil.sendResult(rolePageInfo);
    }

    @PostMapping("/add")
    public JsonObjectResult add(HttpServletRequest request, HttpServletResponse response, TsRole role){
        RoleVo isValid = roleService.findRolesByRoleCode(role.getRoleCode());
        if(null != isValid){ //角色存在
            return ActionUtil.sendResult("角色代码已存在！", false);
        }
        roleService.addRole(request, response, role);
        return ActionUtil.sendResult();
    }

    @PostMapping("/edit")
    public JsonObjectResult edit(HttpServletRequest request, HttpServletResponse response, TsRole role){
        RoleVo isValid = roleService.findRolesByRoleCode(role.getRoleCode());
        RoleVo before = roleService.findRolesByRoleId(role.getId());
        if(!isValid.getId().equals(before.getId())){
            return ActionUtil.sendResult("角色代码已存在！", false);
        }
        BeanUtils.copyProperties(before, role, "roleCode", "roleName");
        roleService.editRole(request, response, role);
        return ActionUtil.sendResult();
    }

    @PostMapping("/savePermission")
    public JsonObjectResult savePermission(String menuIds, String roleId, String permissionCode, String saveButtonIdStr, String saveTableIdStr){
        String[] menuStrIdsArr = menuIds.split(",");
        Integer[] menuIdsArr = new Integer[menuStrIdsArr.length];
        if (StringUtils.isNotBlank(menuStrIdsArr[0])) {
            for (int i=0; i<menuStrIdsArr.length; i++)
                menuIdsArr[i] = Integer.valueOf(menuStrIdsArr[i]);
        }

        String[] saveButtonStrIdsArr = saveButtonIdStr.split(",");
        Integer[] saveButtonIdsArr = new Integer[saveButtonStrIdsArr.length];
        if (StringUtils.isNotBlank(saveButtonStrIdsArr[0])) {
            for (int i = 0; i < saveButtonStrIdsArr.length; i++)
                saveButtonIdsArr[i] = Integer.valueOf(saveButtonStrIdsArr[i]);
        }
        String[] saveTableStrIdsArr = saveTableIdStr.split(",");
        Integer[] saveTableIdsArr = new Integer[saveTableStrIdsArr.length];
        if (StringUtils.isNotBlank(saveTableStrIdsArr[0])) {
            for (int i=0; i<saveTableStrIdsArr.length; i++)
                saveTableIdsArr[i] = Integer.valueOf(saveTableStrIdsArr[i]);
        }

        try {
            roleService.savePermission(Integer.valueOf(roleId), permissionCode, menuIdsArr, saveButtonIdsArr, saveTableIdsArr);
        } catch (Exception e) {
            return ActionUtil.sendResult(e.getMessage(), false);
        }
        return ActionUtil.sendResult();
    }

    /**
     * 逻辑删除
     * @param ids
     * @return
     */
    @PostMapping("/remove")
    public JsonObjectResult remove(HttpServletRequest request, HttpServletResponse response, String ids){
        ids = ids.substring(1, ids.length()-1);
        UserVo currentUser = super.getCurrentUser(request, response);
        if(StringUtils.isNotBlank(ids)){
            roleService.remove(ids, currentUser);
        }
        return ActionUtil.sendResult();
    }

    @GetMapping("/findById")
    public JsonObjectResult findRoleById(Integer roleId){
        RoleVo roleVo = roleService.findRolesByRoleId(roleId);
        return ActionUtil.sendResult(roleVo);
    }

    @GetMapping("/findMenus")
    public JsonObjectResult findMenus(boolean onlyParent, Integer roleId, String parentCode){
        List<TsMenu> menuList = menuService.findMenus(onlyParent, roleId, parentCode);
        return ActionUtil.sendResult(menuList);
    }

    @GetMapping("/findMenusCheckType")
    public JsonObjectResult findMenusCheckType(boolean onlyParent, Integer roleId, String parentCode){
        List<Map<String, Object>> menuList = menuService.findMenusCheckType(onlyParent, roleId, parentCode);
        return ActionUtil.sendResult(menuList);
    }

    @GetMapping("/findPermissionCheckType")
    public JsonObjectResult findPermissionButtonCheckType(String permissionTable, String roleId){
        List<Map<String, Object>> buttonList = menuService.findPermissionButtonCheckType(permissionTable, null, roleId);
        List<Map<String, Object>> tableList = menuService.findPermissionTableCheckType(permissionTable, null, roleId);
        Map<String, Object> permissionMap = new HashMap<String, Object>();
        permissionMap.put("buttonPermission", buttonList);
        permissionMap.put("tablePermission", tableList);
        return ActionUtil.sendResult(permissionMap);
    }
}
