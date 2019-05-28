package br.edu.utfpr.pb.trabalhofinalweb1.repository;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Role;
import br.edu.utfpr.pb.trabalhofinalweb1.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryUser extends JpaRepository<User, Long> {

    User findByUsername(String username);

    Page<User> findAllByRolesContainingOrderByNameAsc(Role role, Pageable pageable);
}
