package com.beta.replyservice;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class ReplyServiceTest {

    private static ReplyService replyService;

    @BeforeAll
    private static void init(){
        replyService=new ReplyService();
    }

    @Test
    void testApplyRules() {
        String rule = "12";
        String input = "kbzw9ru";
        String expectedOutput = "5a8973b3b1fafaeaadf10e195c6e1dd4";

        // Test the applyRules method
        String actualOutput = replyService.applyRules(rule, input);

        assertEquals(expectedOutput, actualOutput);
    }
    @Test
    void testApplyRules2() {
        String rule = "22";
        String input = "kbzw9ru";
        String expectedOutput = "e8501e64cf0a9fa45e3c25aa9e77ffd5";

        // Test the applyRules method
        String actualOutput = replyService.applyRules(rule, input);

        assertEquals(expectedOutput, actualOutput);
    }

}
