package com.beta.replyservice;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReplyController.class)
public class ReplyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReplyService replyService;

    void init(){
        Map<Character, Function<StringBuilder, StringBuilder>> ruleMap = new HashMap<>();
        ruleMap.put('1', StringBuilder::reverse);
        ruleMap.put('2', s -> new StringBuilder(DigestUtils.md5Hex(s.toString())));

        when(replyService.getRuleMap()).thenReturn(ruleMap);
    }
    @Test
    void getV2Reply_PositiveCase() throws Exception {
        String rule = "12";
        String input = "kbzw9ru";
        String expectedOutput = "5a8973b3b1fafaeaadf10e195c6e1dd4";
        init();
        // Mocking the service behavior
        when(replyService.applyRules(rule, input)).thenReturn(expectedOutput);

        mockMvc.perform(get("/v2/reply/{rule}-{input}", rule, input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedOutput));
    }

    @Test
    void getV2Reply_RuleNotFound() throws Exception {
        String rule = "34"; // Invalid rule
        String input = "kbzw9ru";
        init();

        mockMvc.perform(get("/v2/reply/{rule}-{input}", rule, input))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid input"));
    }

    @Test
    void getV2Reply_InvalidInput() throws Exception {
        String rule = "12";
        String input = "invalid-input";
        init();
        mockMvc.perform(get("/v2/reply/{rule}-{input}", rule, input))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid input"));
    }


}
