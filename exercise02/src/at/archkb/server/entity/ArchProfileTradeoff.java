package at.archkb.server.entity;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "hasTradeoff")
public class ArchProfileTradeoff extends GenericEntityImpl<ArchProfileTradeoff, Integer> {

	private static final long serialVersionUID = 1L;

	public ArchProfileTradeoff() {
		super(ArchProfileTradeoff.class);
	}
	@Property
	private String description;
	@Property
	private int ordering; 

	// This site owns
	@StartNode
	private ArchProfile archProfile;
	
	@EndNode
	private Tradeoff tradeoff;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArchProfile getArchProfile() {
		return archProfile;
	}

	public void setArchProfile(ArchProfile archProfile) {
		this.archProfile = archProfile;
	}

	public Tradeoff getTradeoff() {
		return tradeoff;
	}

	public void setTradeoff(Tradeoff tradeoff) {
		this.tradeoff = tradeoff;
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
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((tradeoff == null) ? 0 : tradeoff.hashCode());
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
		ArchProfileTradeoff other = (ArchProfileTradeoff) obj;
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (tradeoff == null) {
			if (other.tradeoff != null) {
				return false;
			}
		} else if (!tradeoff.equals(other.tradeoff)) {
			return false;
		}
		return true;
	}
}
