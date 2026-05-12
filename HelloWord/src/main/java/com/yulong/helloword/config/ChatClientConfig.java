package com.yulong.helloword.config;

import org.springframework.ai.chat.client.ChatClient;
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
    public ChatClient deepseekClient(OpenAiChatModel chatModel) {
        return ChatClient
                .builder(chatModel)
                .defaultOptions(OpenAiChatOptions.builder().model("deepseek-v4-pro"))
                .defaultSystem("你是一个演员请列出所有你喜欢过的人")
                //拦截器模式，增强ai能力
                //.defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultAdvisors(new MySimpleLoggerAdvisor()) //添加自定义的日志记录advisor
                .build();
    }

}
