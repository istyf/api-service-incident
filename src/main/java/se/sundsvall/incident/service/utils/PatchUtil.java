package se.sundsvall.incident.service.utils;

import static java.util.Objects.nonNull;

import java.util.function.Consumer;

public final class PatchUtil {

	private PatchUtil() {
		//never instantiate
	}

	public static <T> void setPropertyIfNonNull(final Consumer<T> setter, final T sourceValue) {
		if (nonNull(sourceValue)) {
			setter.accept(sourceValue);
		}
	}
}
