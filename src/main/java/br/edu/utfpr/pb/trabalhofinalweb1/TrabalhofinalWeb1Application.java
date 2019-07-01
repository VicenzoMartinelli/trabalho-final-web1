package br.edu.utfpr.pb.trabalhofinalweb1;

import br.edu.utfpr.pb.trabalhofinalweb1.config.CrudAuditorAware;
import br.edu.utfpr.pb.trabalhofinalweb1.model.User;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.format.Formatter;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@SpringBootApplication
public class TrabalhofinalWeb1Application {

	public static void main(String[] args) {
		SpringApplication.run(TrabalhofinalWeb1Application.class, args);
	}

	@Bean
	public LayoutDialect layoutDialect() {
		return new LayoutDialect();
	}

    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }

//	@Bean
//	public AuditorAware<User> auditorProvider() {
//		return new CrudAuditorAware();
//	}
}
