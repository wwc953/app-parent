package com.example.apprediscluster.config;

import com.example.apprediscluster.sub.GoodsReceiver;
import com.example.apprediscluster.sub.UserReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * 订阅者配置2
 */
@Configuration
public class ReceiverConfig2 {
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("TextChannel"));
        container.addMessageListener(listenerAdapter, new PatternTopic("kafkaChannel"));
        //配置要订阅的订阅项
        return container;
    }

}
