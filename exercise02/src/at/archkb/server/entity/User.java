package at.archkb.server.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.Email;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnore;

@NodeEntity
public class User extends GenericEntityImpl<User, Integer> implements UserDetails {

	private static final long serialVersionUID = 1L;

	// Login name = Email Address
	@Email
	private String username;

	@JsonIgnore
	private String mailAddressActivationToken;

	// TODO: BCrypt password length check
	@JsonIgnore
	private String password;
	
	@JsonIgnore
	private Boolean enabled = Boolean.TRUE;

	// Locked until email was Verified
	@JsonIgnore
	private Boolean accountNonLocked = Boolean.TRUE;
	
	@JsonIgnore
	@Relationship(type = "hasAuthority", direction = Relationship.OUTGOING)
	private Set<UserAuthority> authorities = new HashSet<>(0);
	
	// The other site owns
	@Relationship(type = "hasArchProfile", direction = Relationship.OUTGOING)
	private Set<ArchProfile> archProfiles = new HashSet<>(0);
	
	public User() {
		super(User.class);
	}

	public String getMailAddressActivationToken() {
		return mailAddressActivationToken;
	}

	public void setMailAddressActivationToken(String mailAddressActivationToken) {
		this.mailAddressActivationToken = mailAddressActivationToken;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void addAuthority(UserAuthority authority) {
		Assert.notNull(authority);
		authorities.add(authority);
	}
	
	public void removeAuthority(UserAuthority authority) {
		Assert.notNull(authority);
		authorities.remove(authority);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		// Account cannot expire
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		// Accounts credentials cannot expire
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}
}
