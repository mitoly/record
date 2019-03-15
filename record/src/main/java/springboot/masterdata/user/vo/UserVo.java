package springboot.masterdata.user.vo;

import springboot.masterdata.role.vo.RoleVo;
import springboot.masterdata.user.entity.TsUser;

import java.util.List;

public class UserVo extends TsUser {// implements UserDetails{
	
	private static final long serialVersionUID = 1492367270612854582L;
	
	private List<RoleVo> roles;

	public List<RoleVo> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleVo> roles) {
		this.roles = roles;
	}

//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//		for(Role role : roles){
//			authorities.add(new SimpleGrantedAuthority(role.getCode()));
//		}
//		return null;
//	}
//
//	@Override
//	public String getUsername() {
//		return this.getName();
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		// TODO Auto-generated method stub
//		return true;
//	}


	
}
