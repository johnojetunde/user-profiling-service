package com.iddera.userprofile.api.persistence.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.iddera.commons.utils.FunctionUtil.emptyIfNullStream;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Converter(autoApply = true)
public class ListOfDateToStringConverter implements AttributeConverter<List<LocalDate>, String> {

    @Override
    public String convertToDatabaseColumn(List<LocalDate> dates) {
        return emptyIfNullStream(dates).map(LocalDate::toString).collect(joining(","));
    }

    @Override
    public List<LocalDate> convertToEntityAttribute(String string) {
        return Optional.ofNullable(string)
                .map(s -> Arrays.stream(s.split(","))
                        .map(LocalDate::parse)
                        .collect(toList()))
                .orElse(new ArrayList<>());
    }
}