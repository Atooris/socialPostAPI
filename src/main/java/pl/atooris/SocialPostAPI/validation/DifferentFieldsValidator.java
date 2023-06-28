package pl.atooris.SocialPostAPI.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class DifferentFieldsValidator implements ConstraintValidator<DifferentFields, Object> {

    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(DifferentFields constraintAnnotation) {
        this.firstFieldName = constraintAnnotation.first();
        this.secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Object firstValue = BeanUtils.getPropertyDescriptor(value.getClass(), firstFieldName)
                    .getReadMethod()
                    .invoke(value);
            Object secondValue = BeanUtils.getPropertyDescriptor(value.getClass(), secondFieldName)
                    .getReadMethod()
                    .invoke(value);

            return !Objects.equals(firstValue, secondValue);
        } catch (IllegalAccessException | InvocationTargetException e) {
            return false;
        }
    }
}
