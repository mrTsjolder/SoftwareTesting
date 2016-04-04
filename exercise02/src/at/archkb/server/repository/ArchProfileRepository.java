package at.archkb.server.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import at.archkb.server.entity.ArchProfile;

public interface ArchProfileRepository extends GraphRepository<ArchProfile>{
	
}
