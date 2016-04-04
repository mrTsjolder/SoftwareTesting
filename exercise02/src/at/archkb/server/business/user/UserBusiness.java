package at.archkb.server.business.user;

import org.springframework.security.provisioning.UserDetailsManager;

import at.archkb.server.entity.User;

public interface UserBusiness extends UserDetailsManager {
	User getUser(String username);
}
