package me.michqql.gui.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A namespace key is a unique key
 * Each key can only be used once
 * An error will be thrown if the same key is created twice
 */
public class NamespaceKey {

    //Static
    private static final Map<String, NamespaceKey> keys = new HashMap<>();
    private static final NamespaceKey NONE = new NamespaceKey("null");

    /**
     * Returns the name-spaced key by the key
     * @param key the string key to find
     * @return a name-spaced version of the key
     */
    public static NamespaceKey getNamespaceKey(String key) {
        return keys.getOrDefault(key, NONE);
    }

    //Non-static
    private final String key; //the unique identifier

    /**
     * Creates a new namespace key
     * @param key the string to represent
     */
    public NamespaceKey(String key) {
        Objects.requireNonNull(key, "Cannot have null namespace key");
        if(keys.containsKey(key))
            throw new IllegalArgumentException("Cannot have duplicate namespace key");

        this.key = key.toLowerCase();
        keys.put(this.key, this);
    }

    /**
     * Returns the key
     * @return string value of key
     */
    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        NamespaceKey that = (NamespaceKey) o;
        return Objects.equals(key, that.key);
    }

    /**
     * Checks if the two strings are equal
     * @param string the other key to check
     * @return {@code true} if they are equal
     */
    public boolean equals(String string) {
        return key.equals(string);
    }
}
