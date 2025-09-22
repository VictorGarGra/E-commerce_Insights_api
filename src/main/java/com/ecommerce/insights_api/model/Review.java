package com.ecommerce.insights_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Establecemos una relación: Muchas reseñas pertenecen a un producto
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private String userComment;
    private int rating; // Puntuación de 1 a 5
    private String sentiment; // Será llenado por la IA: POSITIVE, NEGATIVE, NEUTRAL
}