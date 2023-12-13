package com.elmenus.DronesTransportation.domain.entities;

import com.elmenus.DronesTransportation.utils.ModelEnum;
import com.elmenus.DronesTransportation.utils.StateEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "drones")
public class Drone {
    @Id
    @Size(min = 1, message = "{validation.name.size.too_short}")
    @Size(max = 100, message = "{validation.name.size.too_long}")
    @Column(name = "serial_number")
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "model")
    private ModelEnum model;

    @DecimalMin(value = "0", message = "Please Enter a valid weight limit")
    @DecimalMax(value = "500", message = "Please Enter a valid weight limit")
    @NotNull
    @Column(name = "weight_limit")
    private Double weightLimit;

    @Max(value = 100, message = "batteryCapacity cannot be more than 100")
    @Min(value = 0, message = "batteryCapacity cannot be less than 0")
    @NotNull
    @Column(name = "battery_capacity")
    private Integer batteryCapacity;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "state")
    private StateEnum state;

    @OneToMany(mappedBy = "drone", fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JsonBackReference
    private List<Medication> medicationList;
}