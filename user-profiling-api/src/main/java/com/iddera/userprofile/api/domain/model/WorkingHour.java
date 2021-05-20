package com.iddera.userprofile.api.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iddera.userprofile.api.persistence.formatter.LocalTimeDeserializer;
import com.iddera.userprofile.api.persistence.formatter.LocalTimeSerializer;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
@Embeddable
public class WorkingHour {
    @NotNull(message = "can not be null")
    @JsonFormat(pattern = "HH:mm:ss")
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime startAt;

    @NotNull(message = "can not be null")
    @JsonFormat(pattern = "HH:mm:ss")
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime endAt;

}
