package com.yulong.helloword.controller;


import java.util.Arrays;

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


}
