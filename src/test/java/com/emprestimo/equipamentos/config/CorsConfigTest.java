package com.emprestimo.equipamentos.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CorsConfigTest {

    private static final String[] ALLOWED_ORIGINS = {
            "http://localhost:3000",
            "http://127.0.0.1:3000"
    };

    private static final String[] ALLOWED_METHODS = {
            "GET", "POST", "PUT", "DELETE", "OPTIONS"
    };

    @Autowired
    private MockMvc mockMvc;

    @Test
    void allowsExpectedOriginsMethodsAndHeadersWithoutCredentials() throws Exception {
        for (String origin : ALLOWED_ORIGINS) {
            for (String method : ALLOWED_METHODS) {
                mockMvc.perform(options("/equipamentos")
                                .header("Origin", origin)
                                .header("Access-Control-Request-Method", method)
                                .header(
                                        "Access-Control-Request-Headers",
                                        "accept,content-type,authorization"
                                ))
                        .andExpect(status().isOk())
                        .andExpect(header().string("Access-Control-Allow-Origin", origin))
                        .andExpect(header().string(
                                "Access-Control-Allow-Methods",
                                containsString(method)
                        ))
                        .andExpect(header().string(
                                "Access-Control-Allow-Headers",
                                containsString("authorization")
                        ))
                        .andExpect(header().doesNotExist(
                                "Access-Control-Allow-Credentials"
                        ));
            }
        }
    }
}
