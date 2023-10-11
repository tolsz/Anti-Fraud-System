package antifraud.Annotations;

import antifraud.Constraints.FormattingConstraints;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidCardNumberValidator implements ConstraintValidator<ValidCardNumber, String> {
    private String errorMessage;

    @Override
    public void initialize(ValidCardNumber constraintAnnotation) {
        errorMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String cardNumber, ConstraintValidatorContext context) {
        return FormattingConstraints.isValidCardNumber(cardNumber);
    }
}
