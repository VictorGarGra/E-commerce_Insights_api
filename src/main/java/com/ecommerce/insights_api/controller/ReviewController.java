package com.ecommerce.insights_api.controller;
import com.ecommerce.insights_api.model.Review;
import com.ecommerce.insights_api.repository.ReviewRepository;
import com.ecommerce.insights_api.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:4200") // <--- AÑADE ESTA LÍNEA
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    // Endpoint para crear una nueva reseña
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        Review savedReview = reviewService.createReview(review);
        return ResponseEntity.ok(savedReview);
    }

    // Endpoint para obtener todas las reseñas de un producto específico
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getReviewsByProduct(@PathVariable Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        return ResponseEntity.ok(reviews);
    }
}