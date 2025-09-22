package com.ecommerce.insights_api.repository;

import com.ecommerce.insights_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Indica que es un componente de acceso a datos de Spring
public interface ProductRepository extends JpaRepository<Product, Long> {
    // JpaRepository<ClaseDeLaEntidad, TipoDeLaClavePrimaria>
}