package com.elmenus.DronesTransportation.domain.entities;

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
    @JoinColumn(name = "drone_id")
    private Drone drone;
}
