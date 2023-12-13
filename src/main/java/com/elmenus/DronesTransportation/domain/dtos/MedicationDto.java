package com.elmenus.DronesTransportation.domain.dtos;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicationDto {
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9_-]+$")
    @NotBlank
    private String name;

    @NotNull
    private Double weight;

    @Pattern(regexp = "^[A-Z0-9_]+$")
    @NotBlank
    private String code;

    @Lob
    private byte[] image;
}
