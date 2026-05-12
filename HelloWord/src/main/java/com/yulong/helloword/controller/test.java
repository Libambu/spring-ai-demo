package com.yulong.helloword.controller;

import com.yulong.helloword.entity.pjo.ActorMovies;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;

@RestController
class test {

    @Autowired
    @Qualifier("qwenClient")
    private ChatClient qwenClient;

    @Autowired
    @Qualifier("deepseekClient")
    private ChatClient deepseekClient;

    @GetMapping("/ai/qwen")
    String generation(String userInput) {
        return this.qwenClient.prompt()
                .user(userInput)
                .call()
                .content();
    }

    @GetMapping("/ai/deepseek")
    String generationByDs(String userInput) {
        return this.deepseekClient.prompt()
                .user(userInput)
                .call()
                .content();
    }

    //消息流式发送
    @GetMapping(value = "/ai/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE + ";charset=UTF-8")
    Flux<String> generationStream(String userInput) {
        return this.deepseekClient.prompt()
                .user(userInput)
                .stream()
                .content();
    }
    //返回特定的结构体数据
    //结构体定义的字段要比提示词更加优先
    @GetMapping("/ai/json")
    ActorMovies generationJson(String userInput) {
        return this.deepseekClient.prompt()
                .user(userInput)
                .call()
                .entity(ActorMovies.class);
    }
}
