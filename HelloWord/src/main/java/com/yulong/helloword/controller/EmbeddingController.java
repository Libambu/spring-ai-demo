package com.yulong.helloword.controller;


import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.ai.embedding.EmbeddingModel;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class EmbeddingController {
    
    @Autowired
    private EmbeddingModel embeddingModel;

    @GetMapping("/embedding")
    public float[] getEmbedding(String input) {
        float[] embedding = embeddingModel.embed(input);
        log.info("embedding: {}", Arrays.toString(embedding));
        return embedding;
    }

    @GetMapping("/embedding/compare")
    public Map<String, Object> compareEmbedding(String input1, String input2) {
        float[] embedding1 = embeddingModel.embed(input1);
        float[] embedding2 = embeddingModel.embed(input2);

        double cosineSimilarity = cosineSimilarity(embedding1, embedding2);
        double euclideanDistance = euclideanDistance(embedding1, embedding2);

        return Map.of(
                "input1", input1,
                "input2", input2,
                "dimension", embedding1.length,
                "cosineSimilarity", cosineSimilarity,
                "euclideanDistance", euclideanDistance
        );
    }

    private double cosineSimilarity(float[] vector1, float[] vector2) {
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
            norm1 += vector1[i] * vector1[i];
            norm2 += vector2[i] * vector2[i];
        }

        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    private double euclideanDistance(float[] vector1, float[] vector2) {
        double sum = 0.0;

        for (int i = 0; i < vector1.length; i++) {
            double diff = vector1[i] - vector2[i];
            sum += diff * diff;
        }

        return Math.sqrt(sum);
    }


}
