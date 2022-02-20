package cu.kareldv.reflectionvisitor;

import cu.kareldv.reflectionvisitor.visitors.ClassVisitor;
import cu.kareldv.reflectionvisitor.visitors.FieldVisitor;
import cu.kareldv.reflectionvisitor.visitors.MethodVisitor;
import cu.kareldv.reflectionvisitor.visitors.ParameterVisitor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Karel
 */
public final class ReflectionVisitor {
    public static ReflectionVisitor INSTANCE = new ReflectionVisitor();

    ReflectionVisitor() {
    }
    
    public void accept(Class<?> clasz, ClassVisitor cv){
        if(clasz==null)throw new NullPointerException("The class to be visited cannot be null");
        if(cv==null) throw new NullPointerException("The class visitor cannot be null");
        
        visitClass(clasz, cv);
    }

    private void visitClass(Class<?> clasz, ClassVisitor cv) {
        cv.visitStarted(clasz);
        Filter<Annotation> annF = cv.annotationFilter();
        ArrayList<Annotation> annots = new ArrayList<>();
        
        for (var ann : clasz.getDeclaredAnnotations()) {
            if(annF.accept(ann)){
                annots.add(ann);
            }
        }
        cv.visitAnnotations(Collections.unmodifiableList(annots));
        cv.visitAccessModifiers(clasz.getModifiers());
        
        ClassType type = null;
        if(clasz.isEnum()){
            type=ClassType.ENUM;
        }else if(clasz.isAnnotation()){
            type=ClassType.ANNOTATION;
        }else if(clasz.isInterface()){
            type=ClassType.INTERFACE;
        }else{
            type=ClassType.CLASS;
        }
        
        cv.visitClassType(type);
        
        cv.visitClassName(clasz.getName());
        
        ClassVisitor cv2 = cv.visitSuperclass(clasz.getSuperclass());
        if(cv2!=null){
            visitClass(clasz.getSuperclass(), cv2);
        }
        
        ArrayList<Class<?>> interfaces = new ArrayList();
        Filter<Class<?>> ifF = cv.interfaceFilter();
        
        for (Class<?> aInterface : clasz.getInterfaces()) {
            if(ifF.accept(aInterface)){
                interfaces.add(aInterface);
            }
        }
        
        cv.visitImplementedInterfaces(Collections.unmodifiableList(interfaces));
        
        Filter<Field> fieldF = cv.fieldFilter();
        
        for (Field field : clasz.getDeclaredFields()) {
            if(fieldF.accept(field)){
                FieldVisitor fieldV = cv.visitField(field);
                if(fieldV!=null){
                    visitField(field, fieldV);
                }
            }
        }
        
        Filter<Method> methodF = cv.methodFilter();
        
        for (Method method : clasz.getDeclaredMethods()) {
            if(methodF.accept(method)){
                MethodVisitor methodV = cv.visitMethod(method);
                if(methodV!=null){
                    visitMethod(method, methodV);
                }
            }
        }
        
        cv.visitEnd(clasz);
    }

    private void visitField(Field field, FieldVisitor fieldV) {
        fieldV.visitStarted(field);
        Filter<Annotation> annots = fieldV.annotationFilter();
        ArrayList<Annotation> annotations = new ArrayList<>();
        
        for (Annotation declaredAnnotation : field.getDeclaredAnnotations()) {
            if(annots.accept(declaredAnnotation)){
                annotations.add(declaredAnnotation);
            }
        }
        
        fieldV.visitAnnotations(Collections.unmodifiableList(annotations));
        
        fieldV.visitAccessModifiers(field.getModifiers());
        
        fieldV.visitType(field.getType());
        
        fieldV.visitEnd(field);
    }

    private void visitMethod(Method method, MethodVisitor methodV) {
        methodV.visitStarted(method);
        
        Filter<Annotation> annotF = methodV.annotationFilter();
        ArrayList<Annotation> annots = new ArrayList<>();
        
        for (Annotation declaredAnnotation : method.getDeclaredAnnotations()) {
            if(annotF.accept(declaredAnnotation)){
                annots.add(declaredAnnotation);
            }
        }
        
        methodV.visitAnnotations(Collections.unmodifiableList(annots));
        
        methodV.visitAccessModifiers(method.getModifiers());
        
        methodV.visitReturnType(method.getReturnType());
        
        methodV.visitMethodName(method.getName());
        
        Filter<Parameter> parF = methodV.parameterFilter();
        
        for (Parameter parameter : method.getParameters()) {
            if(parF.accept(parameter)){
                ParameterVisitor parameterV = methodV.visitParameter(parameter);
                if(parameterV!=null){
                    visitParameter(parameter, parameterV);
                }
            }
        }
        
        Filter<Class<?>> throwsClauseF = methodV.throwsClaseFilter();
        ArrayList<Class<?>> tcarray = new ArrayList<>();
        
        for (Class<?> exceptionType : method.getExceptionTypes()) {
            if(throwsClauseF.accept(exceptionType)){
                tcarray.add(exceptionType);
            }
        }
        
        methodV.visitThrowsClauses(Collections.unmodifiableList(tcarray));
        methodV.visitEnd(method);
    }

    private void visitParameter(Parameter param, ParameterVisitor paramV) {
        paramV.visitStarted(param);
        
        Filter<Annotation> annotF = paramV.annotationFilter();
        ArrayList<Annotation> annots = new ArrayList<>();
        
        for (Annotation declaredAnnotation : param.getDeclaredAnnotations()) {
            if(annotF.accept(declaredAnnotation)){
                annots.add(declaredAnnotation);
            }
        }
        
        paramV.visitAnnotations(Collections.unmodifiableList(annots));
        
        paramV.visitAccessModifiers(param.getModifiers());
        
        paramV.visitParameterType(param.getType());
        
        paramV.visitParameterName(param.getName());
        
        paramV.visitEnd(param);
    }
}
