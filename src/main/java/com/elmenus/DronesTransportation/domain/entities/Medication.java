package com.elmenus.DronesTransportation.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "medications")
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9_-]+$")
    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "weight")
    private Double weight;

    @Pattern(regexp = "^[A-Z0-9_]+$")
    @NotBlank
    @Column(name = "code")
    private String code;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "drone_id", insertable = false, updatable = false)
    @JsonIgnore
    private Drone drone;

    @Column(name = "drone_id")
    private String droneId;

    public Medication(String name, Double weight, String code, byte[] image, Drone drone, String droneId) {
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.image = image;
        this.drone = drone;
        this.droneId = droneId;
    }
}
