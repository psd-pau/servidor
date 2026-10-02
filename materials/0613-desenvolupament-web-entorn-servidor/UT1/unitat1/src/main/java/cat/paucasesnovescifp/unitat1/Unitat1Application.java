package cat.paucasesnovescifp.unitat1;

import cat.paucasesnovescifp.unitat1.service.Cotxe;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Unitat1Application {

	public static void main(String[] args) {
		SpringApplication.run(Unitat1Application.class, args);
	}

    @Bean
    CommandLineRunner run(Cotxe cotxe){
        return (String[] args) -> System.out.println(cotxe.conduir());
    }

}
