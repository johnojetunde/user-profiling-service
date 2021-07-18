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
    @NotBlank(message = "Designation is required")
    private String designation;
    @NotBlank(message = "Education Info is required")
    private String educationInfo;
    @NotBlank(message = "Bio is required")
    private String bio;
    @NotBlank(message = "Interest is required")
    private String interest;
}
