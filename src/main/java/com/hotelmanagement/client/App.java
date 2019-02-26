package com.hotelmanagement.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;

import com.hotelmanagement.controller.HotelController;

import reactor.core.publisher.Mono;


@SpringBootApplication
@ComponentScan(basePackages="com.hotelmanagement")
//@EnableScheduling
public class App implements CommandLineRunner
{
	@Autowired
	private HotelController hotelController;
	
    public static void main( String[] args )
    {
    	 SpringApplication app = new SpringApplication(App.class);
         app.setWebApplicationType(WebApplicationType.NONE);
         app.run(args);
    }
    
	ExchangeFilterFunction printlnFilter= new ExchangeFilterFunction() {
	    @Override
	    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
	        System.out.println("\n\n" + request.method().toString().toUpperCase() + ":\n\nURL:"
	                + request.url().toString() + ":\n\nHeaders:" + request.headers().toString() +
	                "\n\nAttributes:"  + request.attributes() + "\n\n");

	        return next.exchange(request);
	    }
	};
	
    @Bean("hotelWebClient")
	public WebClient eventManagementWebClient(){
		return WebClient
				  .builder()
				    .baseUrl("http://localhost:8081")
				   // .defaultCookie("cookieKey", "cookieValue")
				    .defaultHeader("User-Agent", "Spring-boot WebClient")
				    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) 
				    .filter(printlnFilter)
				   // .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8082"))
				  .build();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Application started");
	}
	
	
//	@Scheduled(cron = "* * * * *")
//	public void syncData(){
//		hotelController.syncData();
//	}
}
