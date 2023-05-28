package com.fatec.tcc.tccaudit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TccAuditApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(TccAuditApplication.class, args);
	}

}
