package com.example.apprediscluster.sub;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

@Component
public class RedisSubscriber extends MessageListenerAdapter {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("msg:"+message+"，pattern："+new String(pattern));
    }
}
