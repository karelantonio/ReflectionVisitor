package cu.kareldv.reflectionvisitor.visitors;

import cu.kareldv.reflectionvisitor.ClassType;
import cu.kareldv.reflectionvisitor.Filter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @author Karel
 */
public interface ClassVisitor {
    default Filter<Annotation> annotationFilter(){
        return (arg) -> true;
    };
    default Filter<Class<?>> interfaceFilter(){
        return (arg) -> true;
    }
    default Filter<Field>  fieldFilter(){
        return (arg) -> true;
    }
    default Filter<Method> methodFilter(){
        return (arg) -> true;
    }
    default Filter<Class<?>> subclassFilter(){
        return (arg) -> true;
    }
    
    void visitStarted(Class<?> clasz);
    void visitAnnotations(List<Annotation> annotations);
    void visitAccessModifiers(int modifiers);
    void visitClassType(ClassType type);
    void visitClassName(String name);
    ClassVisitor visitSuperclass(Class<?> supr);
    void visitImplementedInterfaces(List<Class<?>> interfaces);
    
    FieldVisitor visitField(Field f);
    MethodVisitor visitMethod(Method m);
    
    void visitEnd(Class<?> clasz);
}
