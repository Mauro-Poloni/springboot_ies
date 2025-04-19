package users_ies_project.mauro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "users_ies_project")
@EnableJpaRepositories(basePackages = "users_ies_project.repository")
@EntityScan(basePackages = "users_ies_project.entity")
public class MauroApplication {

	public static void main(String[] args) {
		SpringApplication.run(MauroApplication.class, args);
	}

}
