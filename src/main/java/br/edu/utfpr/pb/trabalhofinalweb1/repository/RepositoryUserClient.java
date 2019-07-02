package br.edu.utfpr.pb.trabalhofinalweb1.repository;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Role;
import br.edu.utfpr.pb.trabalhofinalweb1.model.User;
import br.edu.utfpr.pb.trabalhofinalweb1.model.UserClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface RepositoryUserClient extends JpaRepository<UserClient, Long> {

    UserClient findByUsername(String username);
}
