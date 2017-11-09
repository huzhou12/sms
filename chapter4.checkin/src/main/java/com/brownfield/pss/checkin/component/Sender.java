package com.brownfield.pss.checkin.component;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(CheckinSource.class)
public class Sender {
	public Sender() {}
/*	
	RabbitMessagingTemplate template;

	@Autowired
	Sender(RabbitMessagingTemplate template){
		this.template = template;
	}
	@Bean
	Queue queue() {
		return new Queue("CheckINQ", false);
	}
	
	public void send(Object message){
		template.convertAndSend("CheckINQ", message);
	}
*/
	@Output(CheckinSource.checkinQ)
	@Autowired
	private MessageChannel messageChannel;
	
	public void send(Object message) {
		messageChannel.send(MessageBuilder.withPayload(message).build());
	}
	
}