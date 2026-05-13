package com.yulong.helloword.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.yulong.helloword.advisor.MySimpleLoggerAdvisor;


@Configuration
public class ChatClientConfig {

    @Bean("qwenClient")
    public ChatClient qwenClient(OpenAiChatModel chatModel) {
        return ChatClient
                 .builder(chatModel)
                .defaultOptions(OpenAiChatOptions.builder().model("qwen3.6-plus"))
                .build();
    }

    @Bean("deepseekClient")
    public ChatClient deepseekClient(OpenAiChatModel chatModel,ChatMemory chatMemory) {
        return ChatClient
                .builder(chatModel)
                .defaultOptions(OpenAiChatOptions.builder().model("deepseek-v4-pro"))
                .defaultSystem("你是一个演员请列出所有你喜欢过的人")
                //拦截器模式，增强ai能力
                //.defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultAdvisors(
                    new MySimpleLoggerAdvisor(),
                    MessageChatMemoryAdvisor.builder(chatMemory).build()
                ) //添加自定义的日志记录advisor
                .build();
    }
    //内存型
    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .maxMessages(100) // 设置最大消息数为100
                .chatMemoryRepository(new InMemoryChatMemoryRepository()) // 使用内存存储库
                .build();
    }
}
