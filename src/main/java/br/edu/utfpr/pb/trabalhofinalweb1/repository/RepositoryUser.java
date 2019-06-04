package br.edu.utfpr.pb.trabalhofinalweb1.repository;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Role;
import br.edu.utfpr.pb.trabalhofinalweb1.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface RepositoryUser extends JpaRepository<User, Long> {

    User findByUsername(String username);

    Page<User> findAllByRolesContainingOrderByNameAsc(Role role, Pageable pageable);

    Page<User> findAllByOrderByNameAsc(Pageable pageable);

    @Query("SELECT u.imageUrl FROM User u where u.id = :id")
    String findImageUrlById(Long id);

    @Query("SELECT u.roles FROM User u where u.id = :id")
    Set<Role> findRolesById(Long id);

    @Query("SELECT u.password FROM User u where u.id = :id")
    String findPasswordById(Long id);
}
