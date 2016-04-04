package at.archkb.server.business.archprofile;

import java.util.List;

import at.archkb.server.entity.ArchProfile;
import at.archkb.server.exception.OptimisticLockingException;

public interface ArchProfileBusiness {
	List<ArchProfile> getArchProfiles();

	ArchProfile getArchProfile(Long id);

	ArchProfile createArchProfile(ArchProfile archProfile);

	ArchProfile updateArchProfile(ArchProfile archProfile) throws OptimisticLockingException;

	void deleteArchProfile(Long id);
}
