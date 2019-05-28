package br.edu.utfpr.pb.trabalhofinalweb1.service;

import br.edu.utfpr.pb.trabalhofinalweb1.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IServiceUser extends IServiceCrud<User, Long> {

    Page<User> findByRoleAdmin(Pageable pageable);
}
