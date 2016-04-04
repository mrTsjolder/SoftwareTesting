package at.archkb.server.entity;


import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "hasConstraint")
public class ArchProfileConstraint extends GenericEntityImpl<ArchProfileConstraint, Integer> {

	private static final long serialVersionUID = 1L;

	public ArchProfileConstraint() {
		super(ArchProfileConstraint.class);
	}
	
	@Property
	private String description;
	@Property
	private int ordering; 
	
	@StartNode
	private ArchProfile archProfile;
	
	@EndNode
	private Constraint constraint;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Constraint getConstraint() {
		return constraint;
	}

	public void setConstraint(Constraint constraint) {
		this.constraint = constraint;
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
		result = prime * result + ((constraint == null) ? 0 : constraint.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
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
		ArchProfileConstraint other = (ArchProfileConstraint) obj;
		if (constraint == null) {
			if (other.constraint != null) {
				return false;
			}
		} else if (!constraint.equals(other.constraint)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		return true;
	}
}
