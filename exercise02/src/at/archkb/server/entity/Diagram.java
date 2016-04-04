package at.archkb.server.entity;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Diagram extends GenericEntityImpl<Diagram, Integer>{

	private static final long serialVersionUID = 1L;

	public Diagram() {
		super(Diagram.class);
	}
	private String path;
	private int ordering; 
	
	// This site owns
	@Relationship(type = "hasDiagrams", direction = Relationship.INCOMING)
	private ArchProfile archProfile;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ArchProfile getArchProfile() {
		return archProfile;
	}

	public void setArchProfile(ArchProfile archProfile) {
		this.archProfile = archProfile;
	}

	public int getOrdering() {
		return ordering;
	}

	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Diagram other = (Diagram) obj;
		if (path == null) {
			if (other.path != null) {
				return false;
			}
		} else if (!path.equals(other.path)) {
			return false;
		}
		return true;
	}
}
