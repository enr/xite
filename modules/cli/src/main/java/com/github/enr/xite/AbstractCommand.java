package com.github.enr.xite;

/*
 * utility class for some common functionality
 */
public abstract class AbstractCommand implements Command {

    @Override
    abstract public CommandResult execute(Object args);

    /*
    @SuppressWarnings("unchecked")
    protected static <T> T args(Object obj, Class<T> type)
    {
        // ? (type.isInstance(obj))
        if ((obj != null) && (type.isAssignableFrom(obj.getClass())))
        {
            return (T) obj;
        }
        return null;
    }
    */
}
