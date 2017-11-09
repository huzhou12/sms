package com.brownfield.pss.book.component;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface BookingSource {

	public static String inventoryQ="inventoryQ";
	
	@Output("inventoryQ")
	public MessageChannel inventoryQ();
}
