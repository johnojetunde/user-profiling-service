package com.iddera.userprofile.api.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
@Embeddable
public class WorkingHour {
    @NotNull
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startAt;

    @NotNull
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime endAt;

}
