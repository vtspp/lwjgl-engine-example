package br.com.engine.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class Component {
    private static final Map<Class<?>, Component> components = new HashMap<>(0);

    public void register(Component instance) {
        components.put(instance.getClass(), instance);
    }

    public static final <T> T getInstance (Class<?> identifier) {
        return (T) Optional.of( components.get(identifier))
                .orElseThrow(() -> new RuntimeException(
                        String.format("Instance not registered or key [%s} not found", identifier.getName()))
                );
    }

    public abstract void clear ();

    public void destroy () {
        components.clear();
    }
}