package at.archkb.server.business.archprofile;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import at.archkb.server.business.user.UserBusiness;
import at.archkb.server.entity.ArchProfile;
import at.archkb.server.entity.User;
import at.archkb.server.exception.OptimisticLockingException;
import at.archkb.server.repository.ArchProfileRepository;

@Component
public class ArchProfileBusinessImpl implements ArchProfileBusiness {

	@Autowired
	private Neo4jOperations neo4jOperations;

	@Autowired
	private UserBusiness userBusiness;
	
	@Autowired
	private ArchProfileRepository archProfileRepository;

	@Override
	@Transactional(readOnly = true)
	public List<ArchProfile> getArchProfiles() {

		List<ArchProfile> result = new ArrayList<>();
		result.addAll(neo4jOperations.loadAll(ArchProfile.class, 3));
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public ArchProfile getArchProfile(Long id) {

		// TODO: check other "unvalid" values
		// return empty ArchProfile
		if (id < 0) {
			return new ArchProfile();
		}

		// Fetching by id -> with all the dependencies
		ArchProfile archProfile = neo4jOperations.load(ArchProfile.class, id, 3);

		// When not found -> just return an empty ArchProfile POJO
		if (archProfile == null) {
			archProfile = new ArchProfile();
		}

		return archProfile;
	}

	@Override
	@Transactional
	public ArchProfile createArchProfile(ArchProfile archProfile) {
		Assert.notNull(archProfile);
		setArchProfileForRelations(archProfile);
		
		// Setting the owner of the archProfile -> the authenticated user
		User user = userBusiness.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		Assert.notNull(user);
		archProfile.setOwner(user);

		return archProfileRepository.save(archProfile);
	}

	/**
	 * Help procedure for setting the archProfile for the relations cannot be
	 * trusted to come from the client correctly
	 * 
	 * @param archProfile
	 * 
	 */
	private void setArchProfileForRelations(ArchProfile archProfile) {

		archProfile.getArchProfileArchitecturestyles().forEach(apas -> {
			apas.setArchProfile(archProfile);
		});

		archProfile.getArchProfileConstraints().forEach(apco -> {
			apco.setArchProfile(archProfile);
		});

		archProfile.getArchProfileQualityattributes().forEach(apqa -> {
			apqa.setArchProfile(archProfile);
		});

		archProfile.getArchProfileTradeoffs().forEach(apto -> {
			apto.setArchProfile(archProfile);
		});

		archProfile.getDesigndecisions().forEach(dd -> {
			dd.setArchProfile(archProfile);
		});

		archProfile.getDiagrams().forEach(diag -> {
			diag.setArchProfile(archProfile);
		});

		archProfile.getDrivers().forEach(driv -> {
			driv.setArchProfile(archProfile);
		});
	}

	@Override
	@Transactional
	public ArchProfile updateArchProfile(ArchProfile archProfile) throws OptimisticLockingException {

		try {
			return archProfileRepository.save(archProfile);
		} catch (Exception e) {
			throw new OptimisticLockingException(ArchProfile.class, archProfile.getId());
		}
	}

	@Override
	@Transactional
	public void deleteArchProfile(Long id) {
		archProfileRepository.delete(id);
	}

}
