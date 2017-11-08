package com.tess.bookapigateway;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookApiGatewayController {
	
	@RequestMapping("/")
	String greet(HttpServletRequest req) {
		return "<H1>Book Api Goteway Powered by Zuul</H1>";
	}

}
