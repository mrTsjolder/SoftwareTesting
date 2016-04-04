package at.archkb.server.service.archprofile;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.security.access.prepost.PreAuthorize;

import at.archkb.server.entity.ArchProfile;
import at.archkb.server.exception.OptimisticLockingException;

public interface ArchProfileService {
	
	List<ArchProfile> getArchProfiles();
	
	ArchProfile getArchProfile(@NotNull Long id);
	
	// Must be authenticated to create ArchProfiles
	@PreAuthorize("hasRole('ROLE_USER')")
	ArchProfile createArchProfile(@NotNull ArchProfile archProfile);
	
	// Can only edit ArchProfile if is authenticated and owner
	@PreAuthorize("hasRole('ROLE_USER') and hasPermission(#archProfile, 'OWNER')")
	ArchProfile updateArchProfile(@NotNull ArchProfile archProfile) throws OptimisticLockingException;
	
	// Can only delete ArchProfile if is authenticated and owner
	@PreAuthorize("hasRole('ROLE_USER') and hasPermission(#id, 'at.archkb.server.entity.ArchProfile', 'OWNER')")
	void deleteArchProfile(@NotNull Long id);
}
