package at.archkb.server.entity;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "hasArchitecturestyle")
public class ArchProfileArchitecturestyle extends GenericEntityImpl<ArchProfileArchitecturestyle, Integer> {

	private static final long serialVersionUID = 1L;

	public ArchProfileArchitecturestyle() {
		super(ArchProfileArchitecturestyle.class);
	}
	
	@Property
	private String description;
	@Property
	private int ordering; 
	
	@StartNode
	 private ArchProfile archProfile;
	
	@EndNode
	private Architecturestyle architecturestyle;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Architecturestyle getArchitecturestyle() {
		return architecturestyle;
	}

	public void setArchitecturestyle(Architecturestyle architecturestyle) {
		this.architecturestyle = architecturestyle;
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
		result = prime * result + ((architecturestyle == null) ? 0 : architecturestyle.hashCode());
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
		ArchProfileArchitecturestyle other = (ArchProfileArchitecturestyle) obj;
		if (architecturestyle == null) {
			if (other.architecturestyle != null) {
				return false;
			}
		} else if (!architecturestyle.equals(other.architecturestyle)) {
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
