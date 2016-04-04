package at.archkb.server.entity;

import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.security.core.GrantedAuthority;

@NodeEntity
public class UserAuthority extends GenericEntityImpl<UserAuthority, Integer> implements GrantedAuthority {

	private static final long serialVersionUID = 1L;
	
	private String authority;

	public UserAuthority() {
		super(UserAuthority.class);
	}
	
	public UserAuthority(String authority) {
		this();
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((authority == null) ? 0 : authority.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		UserAuthority other = (UserAuthority) obj;
		if (authority == null) {
			if (other.authority != null) {
				return false;
			}
		} else if (!authority.equals(other.authority)) {
			return false;
		}
		return true;
	}
}
