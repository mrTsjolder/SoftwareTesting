package at.archkb.server.entity;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "hasQualityattribute")
public class ArchProfileQualityattribute extends GenericEntityImpl<ArchProfileQualityattribute, Integer> {

	private static final long serialVersionUID = 1L;

	public ArchProfileQualityattribute() {
		super(ArchProfileQualityattribute.class);
	}
	
	@Property
	private int ordering;
	@Property
	private String description;
	
	@StartNode
	private ArchProfile archProfile;
	
	@EndNode
	private QualityAttribute qualityAttribute;

	public int getOrdering() {
		return ordering;
	}

	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}

	public QualityAttribute getQualityattribute() {
		return qualityAttribute;
	}

	public void setQualityattribute(QualityAttribute qualityAttribute) {
		this.qualityAttribute = qualityAttribute;
	}

	public ArchProfile getArchProfile() {
		return archProfile;
	}

	public void setArchProfile(ArchProfile archProfile) {
		this.archProfile = archProfile;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public QualityAttribute getQualityAttribute() {
		return qualityAttribute;
	}

	public void setQualityAttribute(QualityAttribute qualityAttribute) {
		this.qualityAttribute = qualityAttribute;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((qualityAttribute == null) ? 0 : qualityAttribute.hashCode());
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
		ArchProfileQualityattribute other = (ArchProfileQualityattribute) obj;
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (qualityAttribute == null) {
			if (other.qualityAttribute != null) {
				return false;
			}
		} else if (!qualityAttribute.equals(other.qualityAttribute)) {
			return false;
		}
		return true;
	}
}
