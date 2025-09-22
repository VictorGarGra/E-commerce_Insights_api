package com.ecommerce.insights_api.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity // Le dice a JPA que esta clase es una tabla en la BD
@Data   // Lombok genera getters, setters, toString(), etc. automáticamente
public class Product {
    @Id // Marca este campo como la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    private Long id;
    private String name;
    private String category;
    private double price;
}