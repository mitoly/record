package springboot.masterdata.role.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "TR_USER_ROLE")
public class TrUserRole {

    private Integer userId;
    private Integer roleId;

    public TrUserRole() {
    }

    public TrUserRole(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    @Column(name = "USER_ID")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "ROLE_ID")
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}
