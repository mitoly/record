package springboot.masterdata.role.entity;

import springboot.base.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "TS_ROLE")
public class TsRole extends AbstractEntity {
	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;
	private String roleCode;
	private String roleName;
	
	@Column(name = "ID")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "ROLE_CODE")
	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	@Column(name = "ROLE_NAME")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
