package at.archkb.server.service.archprofile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.archkb.server.business.archprofile.ArchProfileBusiness;
import at.archkb.server.entity.ArchProfile;
import at.archkb.server.exception.OptimisticLockingException;

@Service
public class ArchProfileServiceImpl implements ArchProfileService {
	
	@Autowired
	private ArchProfileBusiness archProfileBusiness;

	@Override
	public List<ArchProfile> getArchProfiles() {
		return archProfileBusiness.getArchProfiles();
	}

	@Override
	public ArchProfile getArchProfile(Long id) {
		return archProfileBusiness.getArchProfile(id);
	}

	@Override
	public ArchProfile createArchProfile(ArchProfile archProfile) {
		return archProfileBusiness.createArchProfile(archProfile);
	}

	@Override
	public ArchProfile updateArchProfile(ArchProfile archProfile) throws OptimisticLockingException {
		return archProfileBusiness.updateArchProfile(archProfile);
	}

	@Override
	public void deleteArchProfile(Long id) {
		archProfileBusiness.deleteArchProfile(id);
	}

}
