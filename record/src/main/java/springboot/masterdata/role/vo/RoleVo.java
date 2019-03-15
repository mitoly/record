package springboot.masterdata.role.vo;

import springboot.masterdata.menu.entity.AbstractPermission;
import springboot.masterdata.menu.entity.TsMenu;
import springboot.masterdata.menu.entity.TsPermissionButton;
import springboot.masterdata.menu.entity.TsPermissionTable;
import springboot.masterdata.role.entity.TsRole;

import java.util.List;

public class RoleVo extends TsRole {

	private List<TsMenu> menus;
	private List<AbstractPermission> permissionTables;
	private List<AbstractPermission> permissionButtons;

	public List<TsMenu> getMenus() {
		return menus;
	}

	public void setMenus(List<TsMenu> menus) {
		this.menus = menus;
	}

	public List<AbstractPermission> getPermissionTables() {
		return permissionTables;
	}

	public void setPermissionTables(List<AbstractPermission> permissionTables) {
		this.permissionTables = permissionTables;
	}

	public List<AbstractPermission> getPermissionButtons() {
		return permissionButtons;
	}

	public void setPermissionButtons(List<AbstractPermission> permissionButtons) {
		this.permissionButtons = permissionButtons;
	}
}
