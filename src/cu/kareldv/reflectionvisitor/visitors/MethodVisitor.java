package cu.kareldv.reflectionvisitor.visitors;

import cu.kareldv.reflectionvisitor.Filter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 *
 * @author Karel
 */
public interface MethodVisitor {
    default Filter<Annotation> annotationFilter(){
        return (arg) -> true;
    }
    default Filter<Parameter> parameterFilter(){
        return (arg) -> true;
    }
    default Filter<Class<?>> throwsClaseFilter(){
        return (arg) -> true;
    }
    
    void visitStarted(Method m);
    void visitAnnotations(List<Annotation> annotations);
    void visitAccessModifiers(int modifiers);
    void visitReturnType(Class<?> type);
    void visitMethodName(String name);
    ParameterVisitor visitParameter(Parameter parameter);
    void visitThrowsClauses(List<Class<?>> throwsClauses);
    void visitEnd(Method m);
}
