package at.archkb.server.security;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import at.archkb.server.entity.ArchProfile;
import at.archkb.server.entity.User;
import at.archkb.server.repository.UserRepository;

/**
 * Customized hasPermissions checks. Easier than to create full featured ACL.
 * 
 * @author Rainer
 *
 */
public class CustomPermissionEvaluator implements PermissionEvaluator {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		if (authentication == null || targetDomainObject == null || permission == null)
			return false;

		// check if authentication has owner permission + if ArchProfile
		// instance
		if ("OWNER".equals(permission.toString()) && targetDomainObject instanceof ArchProfile) {
			// check upon user name if it is really the right user
			return authentication.getName().equals(getArchProfileOwnerFromDb(((ArchProfile) targetDomainObject).getId()).getUsername());
		}

		return false;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		if (authentication == null || targetId == null || targetType == null || permission == null || !(targetId instanceof Long))
			return false;

		// check if authentication has owner permission + if targetType is
		// ArchProfile
		if ("OWNER".equals(permission.toString()) && ArchProfile.class.getName().equals(targetType.toString())) {
			return authentication.getName().equals(getArchProfileOwnerFromDb((Long)targetId).getUsername());
		}

		return false;
	}

	/**
	 * Help method for fetching the ArchProfileOwner from the DB
	 * 
	 * @param id
	 *            ID of the ArchProfile
	 * @return Owner of the ArchProfile if ArchProfile found, else empty User
	 *         Object
	 */
	private User getArchProfileOwnerFromDb(Long id) {
		// Fetch user from DB -> the given
		User user = userRepository.findOwnerOfProfile(id);

		// given target Domain Object not found in DB, or owner not set
		if (user==null)
			return new User();

		return user;
	}

}
