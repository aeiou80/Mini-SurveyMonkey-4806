package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class RestTest {
    @Autowired
    private MockMvc mockMvc;

    /*
    Note you can't test the full extent of authentication with MockMvc because Spring Security is a wrapper around the
    controller. The rest of the testing will be done in WebTest.
    See: https://stackoverflow.com/questions/46793814/spring-mockmvc-doesnt-contain-cookies
     */
    @Test
    public void testAuthentication() throws Exception {
        // Test GET without authenticating, should fail
        this.mockMvc.perform(get("/survey"))
                // .andDo(print())
                .andExpect(status().isUnauthorized());

        // Test registration endpoint
        this.mockMvc.perform(post("/user")
                        .content("{ \"username\": \"user1\", \"password\": \"password\" }")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                // .andDo(print())
                .andExpect(status().isOk());

        // Check logging in with wrong password
        this.mockMvc.perform(post("/login")
                        .content("username=user1&password=wrong")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andDo(print())
                .andExpect(status().isUnauthorized());

        // Check logging in with valid username/password
        this.mockMvc.perform(post("/login")
                        .content("username=user1&password=password")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
