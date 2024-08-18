package com.cswap.common.annotation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * @author ZCY-
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EnumValid.EnumValidator.class})
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface EnumValid {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String enumMethod() default "";

    class EnumValidator implements ConstraintValidator<EnumValid, Enum<?>> {
        private String enumMethod;

        @Override
        public void initialize(EnumValid enumValid) {
            enumMethod = enumValid.enumMethod();
        }

        @Override
        public boolean isValid(Enum<?> anEnum, ConstraintValidatorContext constraintValidatorContext) {
            if (anEnum == null) {
                return false;
            }
            // get Enum class
            Class<?> enumClass = anEnum.getDeclaringClass();
            if (enumMethod.isBlank()) {
                return Arrays.asList(enumClass.getEnumConstants()).contains(anEnum);
            } else {
                try {
                    Method method = enumClass.getMethod(enumMethod);
                    if (!Boolean.TYPE.equals(method.getReturnType()) && !Boolean.class.equals(method.getReturnType())) {
                        throw new RuntimeException(String.format("{%s} method return is not boolean type in the {%s} class", enumMethod, enumClass));
                    }

                    if (!Modifier.isStatic(method.getModifiers())) {
                        throw new RuntimeException(String.format("{%s} method is not static method in the {%s} class", enumMethod, enumClass));
                    }
                    Boolean result = (Boolean) method.invoke(anEnum);
                    return result != null && result;
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
