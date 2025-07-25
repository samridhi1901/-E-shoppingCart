package com.cg.estore.cartservice.servicetests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cg.estore.cartservice.exception.CartNotFoundException;
import com.cg.estore.cartservice.exception.GlobalExceptionHandler;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(GlobalExceptionHandler.class)
public class GlobalExceptionHandlerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHandleCartNotFoundException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cart-not-found"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cart not found"));
    }
}
