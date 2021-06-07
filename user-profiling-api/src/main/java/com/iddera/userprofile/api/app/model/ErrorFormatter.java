package com.iddera.userprofile.api.app.model;

import lombok.experimental.UtilityClass;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@UtilityClass
public class ErrorFormatter {

    public static List<String> format(BindingResult bindingResult) {
        List<String> errors = bindingResult.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .distinct()
                .collect(toList());

        bindingResult.getGlobalErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .distinct()
                .forEach(errors::add);

        return errors;
    }

    public static List<String> format(Set<ConstraintViolation<?>> violations) {
        return violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(toList());
    }
}
