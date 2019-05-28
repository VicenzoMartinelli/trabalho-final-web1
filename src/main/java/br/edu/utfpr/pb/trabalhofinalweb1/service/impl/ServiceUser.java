package br.edu.utfpr.pb.trabalhofinalweb1.service.impl;

import br.edu.utfpr.pb.trabalhofinalweb1.model.User;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryRole;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryUser;
import br.edu.utfpr.pb.trabalhofinalweb1.service.IServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ServiceUser extends ServiceCrud<User, Long>
        implements IServiceUser, UserDetailsService {

    @Autowired
    private RepositoryUser usuarioRepository;
    @Autowired
    private RepositoryRole roleRepository;

    @Override
    protected JpaRepository<User, Long> getRepository() {
        return usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não "
                    + "encontrado!");
        }
        return usuario;
    }

    @Override
    public Page<User> findByRoleAdmin(org.springframework.data.domain.Pageable pageable) {
        return usuarioRepository.findAllByRolesContainingOrderByNameAsc(
                roleRepository.findByName("ROLE_ADMIN"),
                pageable);
    }
}
