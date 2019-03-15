package springboot.masterdata.role.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.base.entity.QueryCriteria;
import springboot.base.service.impl.BaseServiceImpl;
import springboot.masterdata.menu.dao.TrRoleMenuDao;
import springboot.masterdata.menu.dao.TrRolePermissionButtonDao;
import springboot.masterdata.menu.dao.TrRolePermissionTableDao;
import springboot.masterdata.menu.dao.TsMenuDao;
import springboot.masterdata.menu.entity.*;
import springboot.masterdata.menu.service.TsMenuService;
import springboot.masterdata.role.dao.TsRoleDao;
import springboot.masterdata.role.entity.TsRole;
import springboot.masterdata.role.service.TsRoleService;
import springboot.masterdata.role.vo.RoleVo;
import springboot.masterdata.user.vo.UserVo;
import springboot.util.ConstantUtils;
import springboot.util.DateUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class TsRoleServiceImpl extends BaseServiceImpl implements TsRoleService {

    @Autowired
    private TsRoleDao roleDao;
    @Autowired
    private TsMenuDao menuDao;
    @Autowired
    private TrRoleMenuDao roleMenuDao;
    @Autowired
    private TrRolePermissionTableDao rolePermissionTableDao;
    @Autowired
    private TrRolePermissionButtonDao rolePermissionButtonDao;


    @Override
    public PageInfo<TsRole> list(QueryCriteria queryCriteria, TsRole role) {
        PageHelper.startPage(queryCriteria.getCurrentPage(), queryCriteria.getPageSize());
        Example ex = new Example(TsRole.class);
        Example.Criteria criteria = ex.createCriteria();
        criteria.andCondition("MARK_FOR_DELETE=", "0");
        if (StringUtils.isNotBlank(role.getRoleCode())){
            criteria.andLike("roleCode", "%" + role.getRoleCode() + "%");
        }
        if (StringUtils.isNotBlank(role.getRoleName())) {
            criteria.andLike("roleName", "%" + role.getRoleName() + "%");
        }
        ex.setOrderByClause("ID DESC");
        List<TsRole> roleList = roleDao.selectByExample(ex);
        PageInfo<TsRole> rolePageInfo = new PageInfo<TsRole>(roleList);
        return rolePageInfo;
    }

    @Override
    public RoleVo findRolesByRoleId(Integer id) {
        return roleDao.findRolesByRoleId(id);
    }

    @Override
    public RoleVo findRolesByRoleCode(String roleCode) {
        List<RoleVo> roleVos = roleDao.findRolesByRoleCode(roleCode);
        if (!roleVos.isEmpty())
            return roleVos.get(0);
        return null;
    }

    @Override
    public void addRole(HttpServletRequest request, HttpServletResponse response, TsRole role) {
        role.setMarkForDelete(ConstantUtils.ENABLED);
        role.setCreateTime(DateUtils.getCurrentDate());
        role.setCreateUser(super.getCurrentUser(request, response).getId());
        roleDao.insert(role);
    }

    @Override
    public void editRole(HttpServletRequest request, HttpServletResponse response, TsRole role) {
        role.setUpdateTime(DateUtils.getCurrentDate());
        role.setUpdateUser(super.getCurrentUser(request, response).getId());
        role.setOptCounter(null != role.getOptCounter()?role.getOptCounter()+1: 1); //修改次数统计
        roleDao.updateByPrimaryKey(role);
    }

    @Override
    public void remove(String ids, UserVo currentUser) {
        roleDao.remove(ids, currentUser);
    }

    @Override
    public void savePermission(Integer roleId, String permissionCode, Integer[] menuIdsArr, Integer[] saveButtonIdsArr, Integer[] saveTableIdsArr) {
        // 整理菜单
        List<TsMenu> menuList = menuDao.findMenuByIds(menuIdsArr);
        Map<String, String> parentMenuMap = new HashMap<String, String>();
        Iterator it = menuList.iterator();
        while (it.hasNext()) {
            TsMenu menu = (TsMenu) it.next();
            if (StringUtils.isNotBlank(menu.getParentCode())){
                parentMenuMap.putIfAbsent(menu.getParentCode(), menu.getParentCode()); // 存放父类Code, 因为半选的父类不会传到后台，需要自己整理
            } else {
                // 没有父类CODE，说明这里自己是父类菜单，先删除，下面统一进行查询以免重复
                it.remove();
            }
        }
        // 找到父类的菜单
        List<TsMenu> parentMenuList = menuDao.findMenuByPermissionCodes(parentMenuMap.values().toArray(new String[parentMenuMap.values().size()]));
        menuList.addAll(parentMenuList); // 和父类菜单整合准备存入

        menuDao.deleteRoleMenu(roleId, null); // 删除所有菜单权限

        List<TrRoleMenu> roleMenuList = new ArrayList<TrRoleMenu>();
        for (TsMenu menu : menuList) {
            TrRoleMenu roleMenu = new TrRoleMenu(roleId, menu.getId());
            roleMenuList.add(roleMenu);
        }
        roleMenuDao.insertList(roleMenuList);//roleId, saveMenuIdList.toArray(new Integer[saveMenuIdList.size()])); // 保存权限菜单

        if (StringUtils.isNotBlank(permissionCode)) {
            List<TsPermissionTable> tableList = menuDao.findPermissionTableByParam(null, permissionCode, null, null);
            List<Integer> tableIds = new ArrayList<Integer>();
            for (TsPermissionTable table : tableList) {
                tableIds.add(table.getId());
            }
            menuDao.deleteRolePermissionTable(roleId, tableIds.toArray(new Integer[tableIds.size()]));

            List<TsPermissionButton> buttonList = menuDao.findPermissionButtonByParam(null, permissionCode, null, null);
            List<Integer> buttonIds = new ArrayList<Integer>();
            for (TsPermissionButton button : buttonList) {
                buttonIds.add(button.getId());
            }
            menuDao.deleteRolePermissionButton(roleId, buttonIds.toArray(new Integer[buttonIds.size()]));
        }

        List<TrRolePermissionTable> saveTrPermissionTable = new ArrayList<TrRolePermissionTable>();
        for(Integer saveTableId : saveTableIdsArr) {
            TrRolePermissionTable table = new TrRolePermissionTable(roleId, saveTableId);
            saveTrPermissionTable.add(table);
        }
        if (!saveTrPermissionTable.isEmpty()) {
            rolePermissionTableDao.insertList(saveTrPermissionTable);
        }


        List<TrRolePermissionButton> saveTrPermissionButton = new ArrayList<TrRolePermissionButton>();
        for(Integer saveButtonId : saveButtonIdsArr) {
            TrRolePermissionButton button = new TrRolePermissionButton(roleId, saveButtonId);
            saveTrPermissionButton.add(button);
        }
        if (!saveTrPermissionButton.isEmpty()) {
            rolePermissionButtonDao.insertList(saveTrPermissionButton);
        }

    }
}
