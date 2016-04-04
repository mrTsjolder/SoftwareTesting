package at.archkb.server.entity;

import java.io.Serializable;

public interface GenericEntity<T extends GenericEntity<T, ID>, ID extends Serializable> extends Serializable {
	
	Class<T> getEntityClass();
	
	Long getId();
	
	long getVersion();

	void setId(Long id);
	
}
