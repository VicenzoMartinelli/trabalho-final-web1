package br.edu.utfpr.pb.trabalhofinalweb1.config;

import br.edu.utfpr.pb.trabalhofinalweb1.model.User;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class CrudAuditorAware implements AuditorAware<User> {

	@Autowired
	private ServiceUser usuarioService;
	
	@Override
	public Optional<User> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder
											.getContext()
											.getAuthentication();
	
		return Optional.ofNullable( 
				usuarioService.findByUsername(authentication.getName()));
	}

}
