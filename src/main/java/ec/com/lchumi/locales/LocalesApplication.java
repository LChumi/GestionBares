package ec.com.lchumi.locales;

import ec.com.lchumi.locales.config.OpenAPIConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(OpenAPIConfiguration.class)
public class LocalesApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalesApplication.class, args);
	}

}
