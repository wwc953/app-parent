package com.example.apprediscluster;

import com.example.apprediscluster.bean.GoodsMessage;
import com.example.apprediscluster.bean.UserMessage;
import com.example.apprediscluster.pub.Publisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PubSubTest {

    @Autowired
    Publisher publisher;

    @Autowired
    private MockMvc mvc;

    @Test
    public void test1() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/send1")
                .param("message","11111")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    public void test2() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/send2")
                .param("message","22222")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    public void pushMessage() {
        UserMessage userMessage = new UserMessage();
        userMessage.setMsgId(UUID.randomUUID().toString().replace("-",""));
        userMessage.setUserId("1");
        userMessage.setUsername("admin");
        userMessage.setUsername("root");
        userMessage.setCreateStamp(System.currentTimeMillis());
        publisher.pushMessage("user",userMessage);
        GoodsMessage goodsMessage = new GoodsMessage();
        goodsMessage.setMsgId(UUID.randomUUID().toString().replace("-",""));
        goodsMessage.setGoodsType("苹果");
        goodsMessage.setNumber("十箱");
        goodsMessage.setCreateStamp(System.currentTimeMillis());
        publisher.pushMessage("goods",goodsMessage);
    }
}
