package springboot.masterdata.menu.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public abstract class AbstractPermission {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    private String permissionTable;
    private String permissionCode;
    private String permissionName;

    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "PERMISSION_TABLE")
    public String getPermissionTable() {
        return permissionTable;
    }

    public void setPermissionTable(String permissionTable) {
        this.permissionTable = permissionTable;
    }

    @Column(name = "PERMISSION_CODE")
    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    @Column(name = "PERMISSION_NAME")
    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
}
