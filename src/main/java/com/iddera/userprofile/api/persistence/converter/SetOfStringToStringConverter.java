package com.iddera.userprofile.api.persistence.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.iddera.commons.utils.FunctionUtil.emptyIfNullStream;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

@Converter(autoApply = true)
public class SetOfStringToStringConverter implements AttributeConverter<Set<String>, String> {

    @Override
    public String convertToDatabaseColumn(Set<String> strings) {
        return emptyIfNullStream(strings).collect(joining(","));
    }

    @Override
    public Set<String> convertToEntityAttribute(String string) {
        return Optional.ofNullable(string)
                .map(s -> Arrays.stream(s.split(","))
                        .collect(toSet()))
                .orElse(new HashSet<>());
    }
}
