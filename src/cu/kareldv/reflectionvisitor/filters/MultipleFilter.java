package cu.kareldv.reflectionvisitor.filters;

import cu.kareldv.reflectionvisitor.Filter;
import java.lang.annotation.Annotation;

/**
 *
 * @author Karel
 */
public class MultipleFilter<Type> implements Filter<Type> {
    private Filter<Type>[] allowed;
    private LogicType logic;

    public MultipleFilter(LogicType logic, Filter<Type>... allowed) {
        this.allowed = allowed;
        this.logic = logic;
    }

    @Override
    public boolean accept(Type arg) {
        boolean counter;
        
        switch(logic){
            case AND:
                counter=true;
                
                for (Filter<Type> filter : allowed) {
                    counter&=filter.accept(arg);
                    if(!counter)return false;
                }
                
                return true;
            case OR:
                counter = false;
                
                for (Filter<Type> filter : allowed) {
                    counter|=filter.accept(arg);
                    if(counter)return true;
                }
                
                return counter;
            case XOR:
                
                counter=false;
                
                for (Filter<Type> filter : allowed) {
                    counter^=filter.accept(arg);
                }
                return counter;
            default:
                return false;
        }
    }
    
    public static enum LogicType{
        AND,
        OR,
        XOR;
    }
}
