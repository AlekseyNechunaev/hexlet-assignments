package exercise;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;

public class Validator {

    public static List<String> validate(Object obj) {
        List<String> result = new LinkedList<>();
        Predicate<Field> notNullPredicate = field -> isCurrentAnnotation(NotNull.class, field)
                && getValueFromField(field, obj) == null;
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (isValid(field, notNullPredicate)) {
                result.add(field.getName());
            }
        }
        return result;
    }


    public static Map<String, List<String>> advancedValidate(Object obj) {
        Map<String, List<String>> result = new LinkedHashMap<>();
        Map<Class<? extends Annotation>, Predicate<Field>> annotationInfo = new LinkedHashMap<>();
        Predicate<Field> notNullPredicate = field -> isCurrentAnnotation(NotNull.class, field)
                && getValueFromField(field, obj) == null;
        Predicate<Field> minLengthPredicate = field -> getValueFromField(field, obj) == null
                && isCurrentAnnotation(MinLength.class, field)
                || isCurrentAnnotation(MinLength.class, field)
                && ((String) getValueFromField(field, obj)).length() < field.getAnnotation(MinLength.class).minLength();
        annotationInfo.put(NotNull.class, notNullPredicate);
        annotationInfo.put(MinLength.class, minLengthPredicate);
        for (Field field : obj.getClass().getDeclaredFields()) {
            List<String> errors = new LinkedList<>();
            field.setAccessible(true);
            annotationInfo.forEach((key, value) -> {
                if (isValid(field, value)) {
                    errors.add(createMessage(key, field));
                }
            });
            if (!errors.isEmpty()) {
                result.put(field.getName(), errors);
            }
        }
        return result;
    }

    private static boolean isValid(Field field, Predicate<Field> p) {
        return p.test(field);
    }

    private static boolean isCurrentAnnotation(Class<? extends Annotation> annotationClass, Field field) {
        Annotation annotation = field.getDeclaredAnnotation(annotationClass);
        return annotation != null;
    }

    private static Object getValueFromField(Field field, Object o) {
        Object result = null;
        try {
            result = field.get(o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String createMessage(Class<? extends Annotation> annotationClass, Field field) {
        switch (annotationClass.getSimpleName()) {
            case "NotNull" -> {
                return "can not be null";
            }
            case "MinLength" -> {
                MinLength minLength = (MinLength) field.getAnnotation(annotationClass);
                return "length less than " + minLength.minLength();
            }
            default -> throw new RuntimeException("unknown annotation");
        }
    }
}