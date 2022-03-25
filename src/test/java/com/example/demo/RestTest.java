package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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
                        .content("{ \"username\": \"user2\", \"password\": \"password\" }")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                // .andDo(print())
                .andExpect(status().isOk());

        // Check logging in with wrong password
        this.mockMvc.perform(post("/login")
                        .content("username=user2&password=wrong")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andDo(print())
                .andExpect(status().isUnauthorized());

        // Check logging in with valid username/password
        this.mockMvc.perform(post("/login")
                        .content("username=user2&password=password")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testSurvey() throws Exception {
        // Create user
        this.mockMvc.perform(post("/user")
                        .content("{ \"username\": \"user3\", \"password\": \"password\" }")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Create survey
        this.mockMvc.perform(post("/survey")
                        .with(user("user3").password("password"))
                        .content("survey1")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());

        // Try to submit answer, fail because it's not published
        this.mockMvc.perform(post("/answer")
                        .with(user("user3").password("password"))
                        .content("{\"answer\": [], \"survey\": { \"id\": 1 }}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        // Add question successfully
        this.mockMvc.perform(post("/question")
                        .with(user("user3").password("password"))
                        .content("{\"question\": \"Hello?\", \"survey\": { \"id\": 1 }, \"type\": \"TEXT\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Publish survey
        this.mockMvc.perform(post("/survey/1/publish")
                        .with(user("user3").password("password")))
                .andExpect(status().isOk());

        // Fail to add question to published survey
        this.mockMvc.perform(post("/question")
                        .with(user("user3").password("password"))
                        .content("{\"question\": \"Hello?\", \"survey\": { \"id\": 1 }, \"type\": \"TEXT\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        // Try to submit answer again, should succeed
        this.mockMvc.perform(post("/answer")
                        .with(user("user3").password("password"))
                        .content("{\"answer\": [], \"survey\": { \"id\": 1 }}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Close survey
        this.mockMvc.perform(post("/survey/1/close")
                        .with(user("user3").password("password")))
                .andExpect(status().isOk());

        // Cannot publish closed survey
        this.mockMvc.perform(post("/survey/1/publish")
                        .with(user("user3").password("password")))
                .andExpect(status().is4xxClientError());

        // Fail to submit answer
        this.mockMvc.perform(post("/answer")
                        .with(user("user3").password("password"))
                        .content("{\"answer\": [], \"survey\": { \"id\": 1 }}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        // Fail to add new questions to closed survey
        this.mockMvc.perform(post("/question")
                        .with(user("user3").password("password"))
                        .content("{\"question\": \"Hello?\", \"survey\": { \"id\": 1 }, \"type\": \"TEXT\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}
