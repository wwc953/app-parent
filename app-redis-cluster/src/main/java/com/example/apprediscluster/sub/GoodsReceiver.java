package com.example.apprediscluster.sub;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GoodsReceiver extends AbstractReceiver {
    @Override
    public void receiveMessage(Object message) {
        log.info("接收到商品消息：{}", JSON.toJSONString(message));
    }
}
