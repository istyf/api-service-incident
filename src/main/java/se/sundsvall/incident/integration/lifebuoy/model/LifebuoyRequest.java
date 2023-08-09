package se.sundsvall.incident.integration.lifebuoy.model;

import java.util.HashMap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LifebuoyRequest extends HashMap<String, LifebuoyRequest.TypeAndValue> {

	private static final long serialVersionUID = 1888793829800084770L;

	public static Builder builder() {
		return new Builder();
	}

	public record TypeAndValue(LifebuoyRequest.TypeAndValue.Type type, Object value) {

		public enum Type {
			Property,
			GeoProperty,
			Point
		}
	}

	public static class Location {
		TypeAndValue.Type type = TypeAndValue.Type.Point;

		public Location(double[] coordinates) {
			this.coordinates = coordinates;
		}

		public TypeAndValue.Type getType() {
			return type;
		}

		public double[] getCoordinates() {
			return coordinates;
		}

		double[] coordinates;

	}

	public static class Builder extends AbstractBuilder<LifebuoyRequest> {

		public Builder withValue(TypeAndValue.Type type, String key, Object value) {
			addModifier(mod -> mod.put(key, new TypeAndValue(type, value)));
			return this;
		}

		@Override
		protected LifebuoyRequest getTarget() {
			return new LifebuoyRequest();
		}

	}
}
