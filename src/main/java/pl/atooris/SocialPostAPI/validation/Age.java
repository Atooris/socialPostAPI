package pl.atooris.SocialPostAPI.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import pl.atooris.SocialPostAPI.validation.DifferentFieldsValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeValidator.class)
public @interface Age {
    String message() default "Invalid age";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}