package Park.gamewebpage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GamewebpageApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamewebpageApplication.class, args);
	}

}
