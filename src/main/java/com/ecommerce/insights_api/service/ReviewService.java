package com.ecommerce.insights_api.service;

import com.ecommerce.insights_api.model.Review;
import com.ecommerce.insights_api.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value; // <-- 1. IMPORTA @Value
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    // 2. INYECTA LA URL DESDE LA CONFIGURACIÓN

    // Reemplaza la línea del @Value por esto:
    private String mlApiUrl = "https://ia-microservice-ia-sentimiento.onrender.com";

    public Review createReview(Review review) {
        String comment = review.getUserComment();
        // 3. USA LA VARIABLE INYECTADA
        String sentiment = getSentimentFromMLService(comment);
        review.setSentiment(sentiment);
        return reviewRepository.save(review);
    }

    private String getSentimentFromMLService(String text) {
        var requestBody = Collections.singletonMap("text", text);
        try {
            // 4. USA LA VARIABLE INYECTADA AQUÍ TAMBIÉN
            SentimentResponse response = restTemplate.postForObject(mlApiUrl, requestBody, SentimentResponse.class);
            return response != null ? response.getSentiment() : "NEUTRAL";
        } catch (Exception e) {
            System.out.println("Error al llamar a la API de ML: " + e.getMessage());
            return "NEUTRAL";
        }
    }

    private static class SentimentResponse {
        private String sentiment;

        public String getSentiment() {
            return sentiment;
        }

        public void setSentiment(String sentiment) {
            this.sentiment = sentiment;
        }
    }
}