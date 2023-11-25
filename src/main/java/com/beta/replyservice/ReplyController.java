package com.beta.replyservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class ReplyController {

	private final ReplyService replyService;

	@Autowired
	public ReplyController(ReplyService replyService) {
		this.replyService = replyService;
	}


	@GetMapping("/reply")
	public ReplyMessage replying() {
		return new ReplyMessage("Message is empty");
	}

	@GetMapping("/reply/{message}")
	public ReplyMessage replying(@PathVariable String message) {
		return new ReplyMessage(message);
	}

	@GetMapping("/v2/reply/{rule}-{input}")
	public ResponseEntity<ReplyMessage> getV2Reply(@PathVariable String rule, @PathVariable String input) {

		if (!isValidInputFormat(input) || !isValidRule(rule)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ReplyMessage("Invalid input"));
		}

		String modifiedString = replyService.applyRules(rule, input);
		ReplyMessage response = new ReplyMessage(modifiedString);
		return ResponseEntity.ok(response);
	}

	private boolean isValidInputFormat(String input) {
		return input.matches("[a-z0-9]*");
	}

	private boolean isValidRule(String rule) {
		for(char c:rule.toCharArray()){
			if(!replyService.getRuleMap().containsKey(c))
				return false;
		}
		return true;
	}





}