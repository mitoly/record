package springboot.masterdata.menu.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "TR_ROLE_PERMISSION_BUTTON")
public class TrRolePermissionButton {

    private Integer roleId;
    private Integer permissionButtonId;

    public TrRolePermissionButton() {
    }

    public TrRolePermissionButton(Integer roleId, Integer permissionButtonId) {
        this.roleId = roleId;
        this.permissionButtonId = permissionButtonId;
    }

    @Column(name = "ROLE_ID")
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Column(name = "PERMISSION_BUTTON_ID")
    public Integer getPermissionButtonId() {
        return permissionButtonId;
    }

    public void setPermissionButtonId(Integer permissionButtonId) {
        this.permissionButtonId = permissionButtonId;
    }
}
