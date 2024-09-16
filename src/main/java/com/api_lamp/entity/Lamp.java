package com.api_lamp.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lamp")
public class Lamp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String brand;
    private double price;
    private String type; // Ejemplo: "LED", "Incandescente"
    private boolean isAvailable;
}