package at.archkb.server.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import at.archkb.server.entity.User;

public interface UserRepository extends GraphRepository<User>{
	User findByUsername(String username);
	
	@Query("match(n:ArchProfile) where id(n) = {0} match (n)-[:hasOwner]->(u:User) return u")
	User findOwnerOfProfile(long profileId);
}
