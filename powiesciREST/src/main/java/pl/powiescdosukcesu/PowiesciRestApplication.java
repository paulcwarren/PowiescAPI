package pl.powiescdosukcesu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages="pl.powiescdosukcesu")
@EnableJpaRepositories(basePackages = "pl.powiescdosukcesu")
public class PowiesciRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(PowiesciRestApplication.class, args);
	}
	
}
