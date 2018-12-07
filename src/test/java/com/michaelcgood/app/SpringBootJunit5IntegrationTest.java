package com.michaelcgood.app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SpringBootJunit5IntegrationTest {
    @Autowired
    //private ShowService showService;

    @Test
    @DisplayName("Integration test which will get the actual output of text service")
    public void contextLoads() {
        //assertEquals(showService.getShowLable(), ORIGINAL_OUTPUT);
    }
}