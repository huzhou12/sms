package com.tess.search.component;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface SearchSink {
	public static String inventoryQ="inventoryQ";
	
	@Input("inventoryQ")
	public MessageChannel inventoryQ();

}
