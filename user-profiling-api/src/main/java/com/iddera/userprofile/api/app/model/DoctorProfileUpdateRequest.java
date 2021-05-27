package com.iddera.userprofile.api.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DoctorProfileUpdateRequest {
    @NotBlank
    private String designation;
    @NotBlank
    private String educationInfo;
    @NotBlank
    private String bio;
    @NotBlank
    private String interest;
}
