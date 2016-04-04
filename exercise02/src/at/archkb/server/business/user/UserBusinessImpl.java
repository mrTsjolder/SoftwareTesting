package at.archkb.server.business.user;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import at.archkb.server.entity.User;
import at.archkb.server.entity.UserAuthority;
import at.archkb.server.repository.UserAuthorityRepository;
import at.archkb.server.repository.UserRepository;
import at.archkb.server.util.PasswordUtils;

@Component
public class UserBusinessImpl implements UserBusiness {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserAuthorityRepository userAuthorityRepository;
	
	
	@Override
	public void changePassword(String oldPassword, String newPassword) {
	}

	@Override
	@Transactional(readOnly = false)
	public void createUser(UserDetails user) {
		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(user.getPassword());
		
		newUser.setEnabled(true);
		newUser.setAccountNonLocked(true);
		newUser.setMailAddressActivationToken(UUID.randomUUID().toString().replaceAll("-", ""));
		
		// hash password
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		user.getAuthorities().forEach(authority -> {
			UserAuthority auth=userAuthorityRepository.findByAuthority(authority.getAuthority());
			if(auth==null){
				auth = new UserAuthority(authority.getAuthority());			
			}
			newUser.addAuthority(auth);
		});
		
		userRepository.save(newUser);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteUser(String username) {
		User user = getUser(username);
		Assert.notNull(user);

		userRepository.delete(user.getId().longValue());
	}

	@Override
	@Transactional(readOnly = false)
	public void updateUser(UserDetails user) {
		User loadedUser = getUser(user.getUsername());
		Assert.notNull(loadedUser);
		
		// Clear Authorities
		loadedUser.getAuthorities().clear();
		final User updated = userRepository.save(loadedUser);		
		
		// Other (account expired, credentials expired not supported!)
		updated.setEnabled(user.isEnabled());
		updated.setAccountNonLocked(user.isAccountNonLocked());
		
		// Add authorities
		user.getAuthorities().forEach(ga -> {
			updated.addAuthority(new UserAuthority(ga.getAuthority()));
		});
		
		// Only set new password if not empty and not already hashed!
		if(StringUtils.hasText(user.getPassword()) && !PasswordUtils.isBcryptPassword(user.getPassword())) {
			updated.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		
		userRepository.save(updated);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean userExists(String username) {
		return getUser(username) != null;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = getUser(username);
		if(user == null) {
			throw new UsernameNotFoundException("No user with username '" + username + "' exists.");
		}
		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public User getUser(String username) {
		return userRepository.findByUsername(username);
	}
}
