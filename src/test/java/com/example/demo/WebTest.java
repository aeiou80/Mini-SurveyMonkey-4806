package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class WebTest {
    @Test
    void authTest(@Autowired WebTestClient webClient) {
        webClient
                .get().uri("/survey")
                .exchange()
                .expectStatus().isUnauthorized();

        webClient
                .post()
                    .uri("/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue("{ \"username\": \"user1\", \"password\": \"password\" }")
                .exchange()
                .expectStatus().isOk();

        // Get the cookie result from authentication
        ResponseCookie cookie = webClient
                .post()
                    .uri("/login")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .bodyValue("username=user1&password=password")
                .exchange()
                .expectStatus().isOk()
                .expectCookie().exists("JSESSIONID")
                .returnResult(String.class)
                    .getResponseCookies().toSingleValueMap().get("JSESSIONID");

        // Use the obtained cookie to perform the GET, which should now succeed
        webClient
                .get()
                    .uri("/survey")
                    .cookie("JSESSIONID", cookie.getValue())
                .exchange()
                .expectStatus().isOk();
    }
}
