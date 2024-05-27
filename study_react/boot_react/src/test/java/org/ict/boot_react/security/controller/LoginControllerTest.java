package org.ict.boot_react.security.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class LoginControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    public void index_anonymus() throws Exception {
        mockMvc.perform(get("/")) // 1. mockMvc 를 통해, get 메소드를 실행
                .andDo(print()) // 3. 결과 프린트 해라
                .andExpect(status().isOk()); // 2. status 코드값이 ok 가 되었을때

    }

    @Test
    @WithMockUser(username="user01", roles = "USER")
    public void testLogin() throws Exception {
        mockMvc.perform(post("/api/auth/login")) // 1. mockMvc 를 통해, post 메소드를 실행
                .andDo(print()) // 3. 결과 프린트 해라
                .andExpect(status().isOk()); // 2. status 코드값이 ok 가 되었을때

    }


}