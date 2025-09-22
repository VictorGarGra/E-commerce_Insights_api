package com.ecommerce.insights_api.controller;
import com.ecommerce.insights_api.model.Product;
import com.ecommerce.insights_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200") // <--- AÑADE ESTA LÍNEA
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Endpoint para crear un nuevo producto.
     * @param product El producto a crear, viene en el cuerpo de la petición.
     * @return El producto guardado con su ID asignado.
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    /**
     * Endpoint para obtener una lista de todos los productos.
     *      * @param id El ID del producto a buscar.
     * @return Una lista de todos los productos en la base de datos.
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productRepository.findById(id);
        // Si el producto existe, devuélvelo. Si no, devuelve "Not Found".
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}