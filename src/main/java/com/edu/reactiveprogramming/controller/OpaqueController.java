package com.edu.reactiveprogramming.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class OpaqueController {

	@GetMapping("/opaque")
	public Mono<String> testOpaque(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
		return Mono.just(principal.getAttribute("sub") + " is the subject");
	}

	@GetMapping("/bearerToken")
	public Mono<String> foo(BearerTokenAuthentication authentication) {
		return Mono.just(authentication.getTokenAttributes().get("sub") + " is the subject");
	}

}
