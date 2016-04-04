package at.archkb.server.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.archkb.server.business.user.UserBusiness;
import at.archkb.server.entity.User;
import at.archkb.server.entity.UserAuthority;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserBusiness userBusiness;
	
	@Override
	public void createUser(String username, String password, String... authorities) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		
		for(String authority : authorities) {
			user.addAuthority(new UserAuthority(authority));
		}
		
		userBusiness.createUser(user);
	}


	@Override
	public User getUser(String username) {
		return userBusiness.getUser(username);
	}

}
