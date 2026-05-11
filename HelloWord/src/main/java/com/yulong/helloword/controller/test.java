package com.yulong.helloword.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class test {

    @Autowired
    @Qualifier("qwenClient")
    private ChatClient qwenClient;

    @Autowired
    @Qualifier("gptClient")
    private ChatClient gptClient;

    @GetMapping("/ai/qwen")
    String generation(String userInput) {
        return this.qwenClient.prompt()
                .user(userInput)
                .call()
                .content();
    }

    @GetMapping("/ai/deepseek")
    String generationByGpt(String userInput) {
        return this.gptClient.prompt()
                .user(userInput)
                .call()
                .content();
    }
}
