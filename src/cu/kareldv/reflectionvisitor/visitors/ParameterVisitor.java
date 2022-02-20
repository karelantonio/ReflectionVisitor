package cu.kareldv.reflectionvisitor.visitors;

import cu.kareldv.reflectionvisitor.Filter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 *
 * @author Karel
 */
public interface ParameterVisitor {
    default Filter<Annotation> annotationFilter(){
        return (arg) -> true;
    }
    
    void visitStarted(Parameter param);
    void visitAnnotations(List<Annotation> ann);
    void visitAccessModifiers(int modifiers);
    void visitParameterType(Class<?> type);
    void visitParameterName(String name);
    void visitEnd(Parameter param);
}
