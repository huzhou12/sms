package com.tess.book.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import com.tess.book.component.BookingComponent;
import com.tess.book.component.BookingStatus;

@Component
@EnableBinding(CheckinSink.class)
public class Receiver {
	
	@Autowired
	private BookingComponent bookingComponent;
	
	public Receiver() {}
	
/*	
	@Autowired
	public Receiver(BookingComponent bookingComponent){
		this.bookingComponent = bookingComponent;
	}
	@RabbitListener(queues = "CheckINQ")
    public void processMessage(long bookingID ) {
		System.out.println(bookingID);
		bookingComponent.updateStatus(BookingStatus.CHECKED_IN, bookingID);
    }
*/
	@ServiceActivator(inputChannel=CheckinSink.checkInQ)
	public void accept(long bookingId) {
		bookingComponent.updateStatus(BookingStatus.CHECKED_IN, bookingId);
	}
}