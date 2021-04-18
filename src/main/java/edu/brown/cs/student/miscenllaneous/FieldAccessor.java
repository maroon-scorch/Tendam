package edu.brown.cs.student.miscenllaneous;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * A PathMap retrieves entries in a JSONObject with shortened notation.
 */
public class FieldAccessor {
    public final static String DELIMITER = "\\.";
    private final Object root;

    public static class NotFoundException extends Exception {
    }

    public FieldAccessor(Object properties) {
        if (properties == null) {
            throw new NullPointerException();
        }
        this.root = properties;
    }

    private String[] getSteps(String path) {
        return path.split(DELIMITER);
    }

    /**
     * Returns the object at the given path.
     *
     * @param path the path to an object
     * @return the object at the given path
     * @throws NotFoundException if the object at the given location cannot be found (or is null)
     */
    public Object getObject(String path) throws NotFoundException {
        String[] steps = getSteps(path);
        Object obj = this.root;
        try {
            for (String step : steps) {
                obj = ((Map<String, Object>) obj).get(step);
            }
        } catch (ClassCastException | NullPointerException e) {
            throw new NotFoundException();
        }
        if (obj == null) {
            throw new NotFoundException();
        } else {
            return obj;
        }
    }

    /**
     * Returns the String value at the given path.
     *
     * @param path a path, e.g. "profile.name.first"
     * @return the value at the given path
     */
    public Optional<String> get(String path) {
        return get(path, String.class);
    }

    /**
     * Returns the value at the given path
     *
     * @param path a path, e.g. "profile.age"
     * @return the value at the given path
     */
    public <T> Optional<T> get(String path, Class<T> type) {
        try {
            return Optional.of(type.cast(getObject(path)));
        } catch (NotFoundException e) {
            return Optional.empty();
        }
    }
}