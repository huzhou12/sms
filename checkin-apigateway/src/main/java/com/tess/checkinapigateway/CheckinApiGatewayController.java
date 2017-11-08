package com.tess.checkinapigateway;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckinApiGatewayController {
	
	@RequestMapping("/")
	String greet(HttpServletRequest req) {
		return "<H1>Checkin Gateway Powered by Zuul</H1>";
	}

}
