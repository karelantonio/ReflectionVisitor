package cu.kareldv.reflectionvisitor;

/**
 *
 * @author Karel
 */
public interface Filter<Type> {
    public boolean accept(Type arg);
}
