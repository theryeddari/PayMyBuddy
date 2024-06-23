package com.thery.paymybuddy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Main application class for PayMyBuddy application.
 */
@SpringBootApplication
@EnableConfigurationProperties
public class PayMyBuddyApplication {

	private static final Logger logger = LogManager.getLogger(PayMyBuddyApplication.class);

	/**
	 * Main method to start the PayMyBuddy application.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		logger.info("Starting PayMyBuddy application...");
		SpringApplication.run(PayMyBuddyApplication.class, args);
		logger.info("PayMyBuddy application started successfully.");
	}

}