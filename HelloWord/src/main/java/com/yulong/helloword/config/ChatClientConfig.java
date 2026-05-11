package com.yulong.helloword.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean("qwenClient")
    public ChatClient qwenClient(ChatClient.Builder builder) {
        return builder
                .defaultOptions(OpenAiChatOptions.builder().model("qwen3.6-plus"))
                .build();
    }

    @Bean("gptClient")
    public ChatClient gptClient(ChatClient.Builder builder) {
        return builder
                .defaultOptions(OpenAiChatOptions.builder().model("deepseek-v4-pro"))
                .build();
    }
}
