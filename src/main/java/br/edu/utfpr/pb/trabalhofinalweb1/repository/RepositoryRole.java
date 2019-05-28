package br.edu.utfpr.pb.trabalhofinalweb1.repository;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryRole extends JpaRepository<Role, Integer> {

    Role findByName(String name);
}
