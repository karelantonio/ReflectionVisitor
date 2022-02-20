package cu.kareldv.reflectionvisitor.filters;

import cu.kareldv.reflectionvisitor.Filter;

/**
 *
 * @author Karel
 */
public class InstanceOfFilter implements Filter<Object> {
    private Class<?> type;

    public InstanceOfFilter(Class<?> type) {
        this.type = type;
    }

    @Override
    public boolean accept(Object arg) {
        return type.isInstance(arg);
    }
    
}
