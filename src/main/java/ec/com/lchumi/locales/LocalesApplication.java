package ec.com.lchumi.locales;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(
		title = "Gestion de Locales",
		description = "Documentacion Gestion de Locales v1.0" ,
		contact = @Contact(name = "Luis Chumi", email = "luischumi.9@gmail.com")
)
)
public class LocalesApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalesApplication.class, args);
	}

}
