package com.iddera.model;

import lombok.experimental.UtilityClass;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@UtilityClass
public class ErrorFormatter {

    public static List<String> format(BindingResult bindingResult) {
        List<String> errors = bindingResult.getFieldErrors().stream()
                .map(s -> s.getField() + ": " + s.getDefaultMessage())
                .distinct()
                .collect(toList());

        bindingResult.getGlobalErrors().stream()
                .map(s -> s.getObjectName() + ": " + s.getDefaultMessage())
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
