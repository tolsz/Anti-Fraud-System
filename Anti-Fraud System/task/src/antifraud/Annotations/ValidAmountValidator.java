package antifraud.Annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidAmountValidator implements ConstraintValidator<ValidAmount, Long> {
    private String errorMessage;

    @Override
    public void initialize(ValidAmount constraintAnnotation) {
        errorMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Long amount, ConstraintValidatorContext context) {
        return amount != null && amount > 0;
    }
}
