package com.dodonew;

import com.dodonew.controller.HelloController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Created by Bruce on 2017/10/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloControllerTests {
    private MockMvc mockMvc;

    /**
     * 在HelloController这个类还没有添加拦截器支持的
     */
    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
    }

    @Test
    public void testHello() throws Exception {
        //mockMvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
        //        .andExpect(MockMvcResultMatchers.status().isOk())
        //        .andExpect(MockMvcResultMatchers.content().string("hello world"));
        mockMvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("hello world"))
                .andDo(MockMvcResultHandlers.print());
        //mockMvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
        //        .andExpect(MockMvcResultMatchers.status().isOk())
        //        .andExpect(MockMvcResultMatchers.content().string("hello world"))
        //        .andReturn();
    }
}
