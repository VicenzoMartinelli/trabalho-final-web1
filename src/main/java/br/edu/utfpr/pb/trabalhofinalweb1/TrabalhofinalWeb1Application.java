package br.edu.utfpr.pb.trabalhofinalweb1;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TrabalhofinalWeb1Application {

	public static void main(String[] args) {
		SpringApplication.run(TrabalhofinalWeb1Application.class, args);
	}

	@Bean
	public LayoutDialect layoutDialect() {
		return new LayoutDialect();
	}
}
