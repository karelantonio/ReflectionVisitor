package cu.kareldv.reflectionvisitor.visitors;

import cu.kareldv.reflectionvisitor.Filter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 *
 * @author Karel
 */
public interface FieldVisitor {
    default Filter<Annotation> annotationFilter(){
        return (arg) -> true;
    }
    
    void visitStarted(Field f);
    void visitAnnotations(List<Annotation> annotation);
    void visitAccessModifiers(int modifiers);
    void visitType(Class<?> type);
    void visitName(String name);
    void visitEnd(Field f);
}
