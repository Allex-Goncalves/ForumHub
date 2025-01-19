package com.github.allex_goncalves.Forum.Hub.Api.Infra.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator <PasswordValidation, String> {
    @Override
    public void initialize(PasswordValidation constraintAnnotation) {
        // Inicialização do validador se necessário
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        // Verificação usando regex
        boolean hasUpperCase = password.matches(".*[A-Z].*");
        boolean hasLowerCase = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
        boolean hasMinLength = password.length() >= 8;

        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar && hasMinLength;
    }
}
