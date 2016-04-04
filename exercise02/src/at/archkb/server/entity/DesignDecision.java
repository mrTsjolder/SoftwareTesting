package at.archkb.server.entity;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class DesignDecision extends GenericEntityImpl<DesignDecision, Integer>{

	private static final long serialVersionUID = 1L;

	public DesignDecision() {
		super(DesignDecision.class);
	}

	private String name;
	private String rationale;
	private int ordering; 
	
	// This site owns
	@Relationship(type = "hasDesignDecisions", direction = Relationship.INCOMING)
	private ArchProfile archProfile;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRationale() {
		return rationale;
	}

	public void setRationale(String rationale) {
		this.rationale = rationale;
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((rationale == null) ? 0 : rationale.hashCode());
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
		DesignDecision other = (DesignDecision) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (rationale == null) {
			if (other.rationale != null) {
				return false;
			}
		} else if (!rationale.equals(other.rationale)) {
			return false;
		}
		return true;
	}
}
