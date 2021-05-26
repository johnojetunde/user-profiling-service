package com.iddera.userprofile.api.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DoctorProfileUpdateRequest {
    @NotNull
    private String designation;
    @NotNull
    private String educationInfo;
    @NotNull
    private String bio;
    @NotNull
    private String interest;
}
