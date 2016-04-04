package at.archkb.server.service.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import at.archkb.server.entity.User;

public interface UserService {
	void createUser(@NotNull String username, @Size(min = 8) String password, String...authorities);
	
	User getUser(@NotNull String username);
}
