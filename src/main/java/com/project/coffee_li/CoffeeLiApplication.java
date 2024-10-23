package com.project.coffee_li;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoffeeLiApplication {

	public static void main(String[] args) {
		setUpdDBConnection();
		SpringApplication.run(CoffeeLiApplication.class, args);
	}

	/**
	 * Initialize database url, username and password from
	 * environment variable.
	 */
	public static void setUpdDBConnection() {
		Dotenv dotenv = Dotenv.load();
		final String datasource_url = dotenv.get("DB_URL");
		final String datasource_username = dotenv.get("DB_USERNAME");
		final String datasource_password = dotenv.get("DB_PASSWORD");

		System.setProperty("datasource_url", datasource_url);
		System.setProperty("datasource_username", datasource_username);
		System.setProperty("datasource_password", datasource_password);
	}

}
