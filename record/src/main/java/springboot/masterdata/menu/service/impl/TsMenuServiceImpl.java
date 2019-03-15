package springboot.masterdata.menu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.base.service.impl.BaseServiceImpl;
import springboot.masterdata.menu.dao.TsMenuDao;
import springboot.masterdata.menu.entity.TsMenu;
import springboot.masterdata.menu.service.TsMenuService;

import java.util.List;
import java.util.Map;

@Service
public class TsMenuServiceImpl extends BaseServiceImpl implements TsMenuService {

    @Autowired
    private TsMenuDao menuDao;

    @Override
    public List<TsMenu> findMenuByIds(Integer[] ids) {
        return menuDao.findMenuByIds(ids);
    }

    @Override
    public List<TsMenu> findMenuByParam(Integer menuId, String menuName, String menuCode, String permissionCode, String parentCode) {
        return menuDao.findMenuByParam(menuId, menuName, menuCode, permissionCode, parentCode);
    }

    @Override
    public List<TsMenu> findMenus(boolean onlyParent, Integer roleId, String parentCode) {
        return menuDao.findMenus(onlyParent, roleId, parentCode);
    }

    @Override
    public List<Map<String, Object>> findMenusCheckType(boolean onlyParent, Integer roleId, String parentCode) {
        return menuDao.findMenusCheckType(onlyParent, roleId, parentCode);
    }

    @Override
    public List<Map<String, Object>> findPermissionButtonCheckType(String permissionTable, String permissionCode, String roleId) {
        return menuDao.findPermissionButtonCheckType(permissionTable, permissionCode, roleId);
    }

    @Override
    public List<Map<String, Object>> findPermissionTableCheckType(String permissionTable, String permissionCode, String roleId) {
        return menuDao.findPermissionTableCheckType(permissionTable, permissionCode, roleId);
    }

}
