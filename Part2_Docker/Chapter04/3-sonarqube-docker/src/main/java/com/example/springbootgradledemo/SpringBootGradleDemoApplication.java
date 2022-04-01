package com.example.springbootgradledemo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@ComponentScan("com.example.springbootgradledemo")
@SpringBootApplication
public class SpringBootGradleDemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringBootGradleDemoApplication.class, args);
	}

}
