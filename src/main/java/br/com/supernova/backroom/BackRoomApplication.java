package br.com.supernova.backroom;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackRoomApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackRoomApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(){
		return new OpenAPI().info(new Info()
				.title("REST API - Meeting Rooms")
				.description("REST API for the management of meeting rooms to serve as the back end of a frontend application")
				.contact(infoContact())
				.version("1.0")
				.termsOfService("http://swagger.io/terms")
				.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}

	@Bean
	public Contact infoContact(){
		return new Contact()
				.name("William Derek Dias")
				.email("williamdkdevops@gmail.com")
				.url("https://github.com/willdkdevj");
	}
}
