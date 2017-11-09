package com.tess.checkin.component;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CheckinSource {

	public static String checkinQ = "checkInQ";
	
	@Output("checkInQ")
	public MessageChannel checkinQ();
}
