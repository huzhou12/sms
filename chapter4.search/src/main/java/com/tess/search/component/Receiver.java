package com.tess.search.component;

import java.util.Map;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(SearchSink.class)
public class Receiver {
	
	@Autowired
	private SearchComponent searchComponent;
	
	public Receiver() {}

	public Receiver(SearchComponent searchComponent){
		this.searchComponent = searchComponent;
	}
/*	
	@Bean
	Queue queue() {
		return new Queue("SearchQ", false);
	}
	
	@RabbitListener(queues = "SearchQ")
    public void processMessage(Map<String,Object> fare) {
       System.out.println(fare);
       searchComponent.updateInventory((String)fare.get("FLIGHT_NUMBER"),(String)fare.get("FLIGHT_DATE"),(int)fare.get("NEW_INVENTORY"));
       //call repository and update the fare for the given flight
    }
*/	
	@ServiceActivator(inputChannel=SearchSink.inventoryQ)
	public void accept(Map<String, Object> fare) {
		
		String number = (String)fare.get("FLIGHT_NUMBER");
		String date = (String)fare.get("FLIGHT_DATE");
		int inv = (int)fare.get("NEW_INVENTORY");
		
		searchComponent.updateInventory(number,date,inv);
		
	}
}