package com.example.apprediscluster.sub;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserReceiver extends AbstractReceiver {
    @Override
    public void receiveMessage(Object message) {
        log.info("接收到用户消息：{}", JSON.toJSONString(message));
    }
}
