package com.hotelmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.hotelmanagement.service.HotelService;

@EnableScheduling
@Component
public class HotelController {

	@Autowired
	private HotelService hotelService;
	
	@Scheduled(cron = "0 */1 * * * *")
	public void syncData(){
		hotelService.syncData();
	}
}
