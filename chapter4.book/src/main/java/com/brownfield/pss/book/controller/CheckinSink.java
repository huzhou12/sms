package com.brownfield.pss.book.controller;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface CheckinSink {

	public static String checkInQ = "checkInQ";
	
	@Input("checkInQ")
	public MessageChannel messageChennel();
}
