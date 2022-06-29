package co.fastcampus.jpaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class JPAServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JPAServiceApplication.class, args);
	}
}
