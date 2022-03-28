package com.vr.authorizator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EntityScan(basePackages = {"com.vr.authorizator"})
//@ComponentScan("com.vr.authorizator")
@EnableAutoConfiguration
public class AuthorizatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizatorApplication.class, args);
	}

}
