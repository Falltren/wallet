package com.fallt.wallet.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = OperationTypeValidator.class)
public @interface OperationTypeValidation {
    Class<? extends Enum<?>> enumClass();

    String message() default "Incorrect operation type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
