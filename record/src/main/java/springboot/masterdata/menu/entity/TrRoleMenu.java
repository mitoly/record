package springboot.masterdata.menu.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "TR_ROLE_MENU")
public class TrRoleMenu {

    private Integer roleId;
    private Integer menuId;

    public TrRoleMenu() {
    }

    public TrRoleMenu(Integer roleId, Integer menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }

    @Column(name = "ROLE_ID")
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Column(name = "MENU_ID")
    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}
