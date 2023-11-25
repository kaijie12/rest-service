package com.beta.replyservice;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class ReplyService {
    private final Map<Character, Function<StringBuilder, StringBuilder>> ruleMap;


    public ReplyService() {
        ruleMap = new HashMap<>();
        ruleMap.put('1', StringBuilder::reverse);
        ruleMap.put('2', s -> new StringBuilder(DigestUtils.md5Hex(s.toString())));
        ruleMap.put('3', s -> new StringBuilder(StringUtils.capitalize(s.toString())));
        // Add more rules as needed
    }

    public String applyRules(String rule, String input) {
        char[] rules = rule.toCharArray();
        StringBuilder result = new StringBuilder(input);

        for (char r : rules) {
            if (ruleMap.containsKey(r)) {
                result = ruleMap.get(r).apply(result);
            }
        }

        return result.toString();
    }


    public Map<Character, Function<StringBuilder, StringBuilder>> getRuleMap() {
        return ruleMap;
    }


}