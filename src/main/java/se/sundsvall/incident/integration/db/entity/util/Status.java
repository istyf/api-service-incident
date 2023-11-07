package se.sundsvall.incident.integration.db.entity.util;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {
	INSKICKAT(1, "Inskickat"),
	KLART(2, "Klart"),
	KOMPLETTERAD(3, "Kompletterad"),
	SPARAT(4, "Sparat ärende"),
	UNDER_BEHANDLING(5, "Under behandling"),
	VANTAR_KOMPLETTERING(6, "Väntar på komplettering"),
	ARKIVERAD(7, "Ärendet arkiveras"),
	ERROR(8, "Ärendet har inte mailats pga systemfel");

	private final int value;
	private final String label;

	Status(int value, String label) {
		this.value = value;
		this.label = label;
	}

	@JsonCreator
	public static Status forValue(int value) {
		return Arrays.stream(Status.values())
			.filter(status -> value == status.value)
			.findFirst()
			.orElse(null);
	}

	public int getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}
}
