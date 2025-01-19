package com.github.allex_goncalves.Forum.Hub.Api.Infra.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValidation {
    String message() default "Senha inválida. A senha deve ter pelo menos 8 caracteres, com letras maiúsculas, minúsculas, números e caracteres especiais.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
