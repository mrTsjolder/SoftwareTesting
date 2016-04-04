package at.archkb.server.entity;

import java.io.Serializable;
import javax.persistence.Version;

import org.neo4j.ogm.annotation.GraphId;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property="@javaref")
public abstract class GenericEntityImpl<T extends GenericEntity<T, ID>, ID extends Serializable> implements GenericEntity<T, ID>{
	
	private static final long serialVersionUID = 1L;
	
	private static final int equality = 513513834;
	
	@JsonIgnore
	transient protected final Class<T> entityClass;
	
	public GenericEntityImpl(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	@Override
	public Class<T> getEntityClass() {
		return entityClass;
	}
	
	@GraphId
	private Long id;
	
	@Version
	private long version;
	
	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public long getVersion() {
		return version;
	}
	
	@Override
	public int hashCode() {
		return equality;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}
	
	
}
