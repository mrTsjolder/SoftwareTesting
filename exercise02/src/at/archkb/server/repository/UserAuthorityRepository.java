package at.archkb.server.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import at.archkb.server.entity.UserAuthority;

public interface UserAuthorityRepository extends GraphRepository<UserAuthority>{
	
	public UserAuthority findByAuthority(String authority);

}
