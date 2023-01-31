package se.sundsvall.incident.integration.lifebuoy.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractBuilder<T> {

    private final List<Consumer<T>> modifiers = new ArrayList<>();

    protected abstract T getTarget();

    /**
     * Adds a modifier, modifying the target object.
     *
     * @param modifier the modifier to add
     */
    protected final void addModifier(Consumer<T> modifier) {
        modifiers.add(modifier);
    }

    /**
     * Builds the target object, applies all modifiers and returns it.
     *
     * @return the target object
     */
    public final T build() {
        T value = getTarget();
        modifiers.forEach(modifier -> modifier.accept(value));
        return value;
    }
}