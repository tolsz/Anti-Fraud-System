package antifraud.Annotations;

import antifraud.Constraints.FormattingConstraints;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidIPValidator implements ConstraintValidator<ValidIP, String> {
    private String errorMessage;

    @Override
    public void initialize(ValidIP constraintAnnotation) {
        errorMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String ip, ConstraintValidatorContext context) {
        return FormattingConstraints.isValidIp(ip);
    }
}
