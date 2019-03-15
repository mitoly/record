package springboot.masterdata.menu.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "TR_ROLE_PERMISSION_TABLE")
public class TrRolePermissionTable {

    private Integer roleId;
    private Integer permissionTableId;

    public TrRolePermissionTable() {
    }

    public TrRolePermissionTable(Integer roleId, Integer permissionTableId) {
        this.roleId = roleId;
        this.permissionTableId = permissionTableId;
    }

    @Column(name = "ROLE_ID")
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Column(name = "PERMISSION_TABLE_ID")
    public Integer getPermissionTableId() {
        return permissionTableId;
    }

    public void setPermissionTableId(Integer permissionTableId) {
        this.permissionTableId = permissionTableId;
    }
}
