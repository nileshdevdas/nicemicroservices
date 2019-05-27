package com.example.demo;

import java.util.logging.Logger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankServices {
	private Logger logger = Logger.getLogger("bankservice");

	@RequestMapping(path = "version", method = RequestMethod.GET)
	public int getVersion() {
		logger.info("Service Entry getVersion" + System.currentTimeMillis());
		int result = 1;
		logger.info("Server Exit getVersion" + System.currentTimeMillis());
		return result;
	}
}