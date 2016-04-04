package at.archkb.server.entity;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Tradeoff extends GenericEntityImpl<Tradeoff, Integer> {

	private static final long serialVersionUID = 1L;

	public Tradeoff() {
		super(Tradeoff.class);
	}

	@Relationship(type = "hasTradeoffItemOver", direction = Relationship.OUTGOING)
	private TradeoffItem tradeoffItemOver;

	@Relationship(type = "hasTradeoffItemUnder", direction = Relationship.OUTGOING)
	private TradeoffItem tradeoffItemUnder;

	public TradeoffItem getTradeoffItemOver() {
		return tradeoffItemOver;
	}

	public void setTradeoffItemOver(TradeoffItem tradeoffItemOver) {
		this.tradeoffItemOver = tradeoffItemOver;
	}

	public TradeoffItem getTradeoffItemUnder() {
		return tradeoffItemUnder;
	}

	public void setTradeoffItemUnder(TradeoffItem tradeoffItemUnder) {
		this.tradeoffItemUnder = tradeoffItemUnder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((tradeoffItemOver == null) ? 0 : tradeoffItemOver.hashCode());
		result = prime * result + ((tradeoffItemUnder == null) ? 0 : tradeoffItemUnder.hashCode());
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
		Tradeoff other = (Tradeoff) obj;
		if (tradeoffItemOver == null) {
			if (other.tradeoffItemOver != null) {
				return false;
			}
		} else if (!tradeoffItemOver.equals(other.tradeoffItemOver)) {
			return false;
		}
		if (tradeoffItemUnder == null) {
			if (other.tradeoffItemUnder != null) {
				return false;
			}
		} else if (!tradeoffItemUnder.equals(other.tradeoffItemUnder)) {
			return false;
		}
		return true;
	}
}
