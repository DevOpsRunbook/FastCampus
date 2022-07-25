package co.fastcampus.author;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("co.fastcampus.author.repositories")
@EntityScan(basePackages = { "co.fastcampus.author.entities" })
public class AuthorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorApplication.class, args);
	}

}
