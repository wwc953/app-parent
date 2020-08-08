package com.example.appuser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest
@AutoConfigureMockMvc
public class MvcTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;


//    @Test
//    public void controller(){
//        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
//
//        MockHttpServletRequestBuilder builder = post("/local")
//                .contentType(MediaType.APPLICATION_JSON);
//
//
//        ResultActions perform = mockMvc.perform(builder);
//
//    }

    @Test
    public void aa() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/local")).
                andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        int status = mvcResult.getResponse().getStatus();
    }
}
