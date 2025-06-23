
package com.aluracursos.literalura.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class GutendexClient {
    private static final String BASE_URL = "https://gutendex.com/books/?search=";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static JsonNode buscarLibro(String titulo) throws Exception {
        String encoded = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + encoded))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode root = mapper.readTree(response.body());
        if (!root.has("results") || root.get("results").isEmpty()) {
            return null;
        }

        return root.get("results").get(0);
    }
}
