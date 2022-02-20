package cu.kareldv.reflectionvisitor;

import cu.kareldv.reflectionvisitor.visitors.ClassVisitor;
import cu.kareldv.reflectionvisitor.visitors.FieldVisitor;
import cu.kareldv.reflectionvisitor.visitors.MethodVisitor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Karel
 */
public class ReflectionVisitorTest {
    
    public ReflectionVisitorTest() {
    }

    @Test
    public void testAccept() {
        System.out.println("accept");
        Class clasz = String.class;
        ClassVisitor cv = new DebugClassVisitor();
        ReflectionVisitor instance = new ReflectionVisitor();
        instance.accept(clasz, cv);
    }

    private static class DebugClassVisitor implements ClassVisitor {

        public DebugClassVisitor() {
        }

        @Override
        public void visitStarted(Class<?> clasz) {
            System.out.println("Class: "+clasz.getName());
        }

        @Override
        public void visitAnnotations(List<Annotation> annotations) {
            System.out.println("Annotations:");
            for (Annotation annotation : annotations) {
                System.out.println("| "+annotation.annotationType().getName());
            }
        }

        @Override
        public void visitAccessModifiers(int modifiers) {
            System.out.println("Modifiers: "+Modifier.toString(modifiers));
        }

        @Override
        public void visitClassType(ClassType type) {
            System.out.println("Class type: "+type.name());
        }

        @Override
        public void visitClassName(String name) {
            System.out.println("Name: "+name);
        }

        @Override
        public ClassVisitor visitSuperclass(Class<?> supr) {
            System.out.println("Superclass: "+supr.getName());
            return null;
        }

        @Override
        public void visitImplementedInterfaces(List<Class<?>> interfaces) {
            System.out.println("Interfaces:");
            for (Class<?> aInterface : interfaces) {
                System.out.println("| "+aInterface.getName());
            }
        }

        @Override
        public FieldVisitor visitField(Field f) {
            return new DebugFieldVisitor();
        }

        @Override
        public MethodVisitor visitMethod(Method m) {
            return null;
        }

        @Override
        public void visitEnd(Class<?> clasz) {
            System.out.println("Class end: "+clasz.getName());
        }
    }

    private static class DebugFieldVisitor implements FieldVisitor {

        public DebugFieldVisitor() {
        }

        @Override
        public void visitStarted(Field f) {
            System.out.println("Field: "+f.getName());
        }

        @Override
        public void visitAnnotations(List<Annotation> annotation) {
            System.out.println("| Annotations:");
            for (Annotation annotation1 : annotation) {
                System.out.println("| | "+annotation1.annotationType().getName());
            }
        }

        @Override
        public void visitAccessModifiers(int modifiers) {
            System.out.println("| Modifiers: "+Modifier.toString(modifiers));
        }

        @Override
        public void visitType(Class<?> type) {
            System.out.println("| Type: "+type.getName());
        }

        @Override
        public void visitName(String name) {
            System.out.println("| Name: "+name);
        }

        @Override
        public void visitEnd(Field f) {
            System.out.println("Field end: "+f.getName());
        }
    }
    
}
