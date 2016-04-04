package at.archkb.server.entity;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class ArchProfile extends GenericEntityImpl<ArchProfile, Integer>{

	private static final long serialVersionUID = 1L;

	public ArchProfile() {
		super(ArchProfile.class);
	}

	private String title;
	private String description;

	// This site owns
	@Relationship(type = "hasOwner", direction = Relationship.OUTGOING)
	private User owner;

	// The other site owns
	@Relationship(type = "hasDiagrams", direction = Relationship.OUTGOING)
	private Set<Diagram> diagrams = new HashSet<>(0);

	// The other site owns
	@Relationship(type = "hasDesignDecisions", direction = Relationship.OUTGOING)
	private Set<DesignDecision> designDecisions = new HashSet<>(0);

	// The other site owns
	@Relationship(type = "hasDrivers", direction = Relationship.OUTGOING)
	private Set<Driver> drivers = new HashSet<>(0);

	// The other site owns
	@Relationship(type = "hasArchitecturestyle", direction = Relationship.OUTGOING)
	private Set<ArchProfileArchitecturestyle> archProfileArchitecturestyles = new HashSet<>(0);

	// The other site owns
	@Relationship(type = "hasConstraint", direction = Relationship.OUTGOING)
	private Set<ArchProfileConstraint> archProfileConstraints = new HashSet<>(0);

	// The other site owns
	@Relationship(type = "hasQualityattribute", direction = Relationship.OUTGOING)
	private Set<ArchProfileQualityattribute> archProfileQualityattributes = new HashSet<>(0);

	// The other site owns
	@Relationship(type = "hasTradeoff", direction = Relationship.OUTGOING)
	private Set<ArchProfileTradeoff> archProfileTradeoffs = new HashSet<>(0);

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Diagram> getDiagrams() {
		return diagrams;
	}

	public void setDiagrams(Set<Diagram> diagrams) {
		this.diagrams = diagrams;
	}

	public Set<DesignDecision> getDesigndecisions() {
		return designDecisions;
	}

	public void setDesigndecisions(Set<DesignDecision> designDecisions) {
		this.designDecisions = designDecisions;
	}

	public Set<Driver> getDrivers() {
		return drivers;
	}

	public void setDrivers(Set<Driver> drivers) {
		this.drivers = drivers;
	}

	public Set<ArchProfileArchitecturestyle> getArchProfileArchitecturestyles() {
		return archProfileArchitecturestyles;
	}

	public void setArchProfileArchitecturestyles(Set<ArchProfileArchitecturestyle> archProfileArchitecturestyles) {
		this.archProfileArchitecturestyles = archProfileArchitecturestyles;
	}

	public Set<ArchProfileConstraint> getArchProfileConstraints() {
		return archProfileConstraints;
	}

	public void setArchProfileConstraints(Set<ArchProfileConstraint> archProfileConstraints) {
		this.archProfileConstraints = archProfileConstraints;
	}

	public Set<ArchProfileQualityattribute> getArchProfileQualityattributes() {
		return archProfileQualityattributes;
	}

	public void setArchProfileQualityattributes(Set<ArchProfileQualityattribute> archProfileQualityattributes) {
		this.archProfileQualityattributes = archProfileQualityattributes;
	}

	public Set<ArchProfileTradeoff> getArchProfileTradeoffs() {
		return archProfileTradeoffs;
	}

	public void setArchProfileTradeoffs(Set<ArchProfileTradeoff> archProfileTradeoffs) {
		this.archProfileTradeoffs = archProfileTradeoffs;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((archProfileArchitecturestyles == null) ? 0 : archProfileArchitecturestyles.hashCode());
		result = prime * result + ((archProfileConstraints == null) ? 0 : archProfileConstraints.hashCode());
		result = prime * result
				+ ((archProfileQualityattributes == null) ? 0 : archProfileQualityattributes.hashCode());
		result = prime * result + ((archProfileTradeoffs == null) ? 0 : archProfileTradeoffs.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((designDecisions == null) ? 0 : designDecisions.hashCode());
		result = prime * result + ((diagrams == null) ? 0 : diagrams.hashCode());
		result = prime * result + ((drivers == null) ? 0 : drivers.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		ArchProfile other = (ArchProfile) obj;
		if (archProfileArchitecturestyles == null) {
			if (other.archProfileArchitecturestyles != null) {
				return false;
			}
		} else if (!archProfileArchitecturestyles.equals(other.archProfileArchitecturestyles)) {
			return false;
		}
		if (archProfileConstraints == null) {
			if (other.archProfileConstraints != null) {
				return false;
			}
		} else if (!archProfileConstraints.equals(other.archProfileConstraints)) {
			return false;
		}
		if (archProfileQualityattributes == null) {
			if (other.archProfileQualityattributes != null) {
				return false;
			}
		} else if (!archProfileQualityattributes.equals(other.archProfileQualityattributes)) {
			return false;
		}
		if (archProfileTradeoffs == null) {
			if (other.archProfileTradeoffs != null) {
				return false;
			}
		} else if (!archProfileTradeoffs.equals(other.archProfileTradeoffs)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (designDecisions == null) {
			if (other.designDecisions != null) {
				return false;
			}
		} else if (!designDecisions.equals(other.designDecisions)) {
			return false;
		}
		if (diagrams == null) {
			if (other.diagrams != null) {
				return false;
			}
		} else if (!diagrams.equals(other.diagrams)) {
			return false;
		}
		if (drivers == null) {
			if (other.drivers != null) {
				return false;
			}
		} else if (!drivers.equals(other.drivers)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		return true;
	}
}
