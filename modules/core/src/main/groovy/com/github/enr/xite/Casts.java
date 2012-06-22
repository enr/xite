package com.github.enr.xite;

import java.util.Map;

public class Casts
{

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object o)
    {
        return (T) o;
    }

    @SuppressWarnings("unchecked")
    public static <T> T castOrFail(Object obj, Class<T> type)
    {
        // ? type.isAssignableFrom
        if (type.isInstance(obj))
        {
            return (T) obj;
        }
        throw new ClassCastException();
    }

    @SuppressWarnings("unchecked")
    public static <T> T castOrNull(Object obj, Class<T> type)
    {
        // ? (type.isInstance(obj))
        if ((obj != null) && (type.isAssignableFrom(obj.getClass())))
        {
            return (T) obj;
        }
        return null;
    }

    /**
     * ritorna x chiave specificata, oggetto della classe passata
     * Returns, for the specified key, an object casted to the required class, or throws an IllegalArgumentException.
     * Useful for raw maps.
     * 
     */
    @SuppressWarnings("unchecked")
    public static <T> T getFromMap(Map<?,?> m, Object key, Class<T> type)
    {
        Object value = m.get(key);
        if (value == null)
        {
            return null;
        }
        if (!type.isAssignableFrom(value.getClass()))
        {
            throw new IllegalArgumentException("Incorrect type specified for header '" + key
            + "'. Expected [" + type + "] but actual type is [" + value.getClass() + "]");
        }
        return (T) value;
    }
}
