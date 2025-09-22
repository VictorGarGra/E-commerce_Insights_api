package com.ecommerce.insights_api.service;
import com.ecommerce.insights_api.model.Review;
import com.ecommerce.insights_api.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
@Service // Marca esta clase como un servicio de lógica de negocio
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    // RestTemplate es el cliente HTTP de Spring para hacer llamadas a otras APIs
    private final RestTemplate restTemplate = new RestTemplate();

    // La URL de tu microservicio de Python (asegúrate que esté corriendo)
    private final String ML_API_URL = "http://localhost:8000/predict";

    public Review createReview(Review review) {
        // 1. Obtener el texto del comentario
        String comment = review.getUserComment();

        // 2. Llamar al microservicio de IA para obtener el sentimiento
        String sentiment = getSentimentFromMLService(comment);

        // 3. Asignar el sentimiento a la reseña
        review.setSentiment(sentiment);

        // 4. Guardar la reseña completa en la base de datos
        return reviewRepository.save(review);
    }

    private String getSentimentFromMLService(String text) {
        // Creamos el cuerpo de la petición para la API de Python: {"text": "..."}
        var requestBody = Collections.singletonMap("text", text);

        try {
            // Hacemos la llamada POST y esperamos una respuesta de tipo SentimentResponse
            SentimentResponse response = restTemplate.postForObject(ML_API_URL, requestBody, SentimentResponse.class);
            return response != null ? response.getSentiment() : "NEUTRAL"; // Valor por defecto
        } catch (Exception e) {
            // Si la API de Python falla o no está disponible, asignamos un valor por defecto
            System.out.println("Error al llamar a la API de ML: " + e.getMessage());
            return "NEUTRAL";
        }
    }

    // Clase auxiliar para mapear la respuesta JSON de la API de Python
    private static class SentimentResponse {
        private String sentiment;
        // Getters y Setters (Lombok no funciona en clases internas estáticas fácilmente)
        public String getSentiment() { return sentiment; }
        public void setSentiment(String sentiment) { this.sentiment = sentiment; }
    }
}