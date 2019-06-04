package br.edu.utfpr.pb.trabalhofinalweb1.service;

import br.edu.utfpr.pb.trabalhofinalweb1.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IServiceUser extends IServiceCrud<User, Long> {

    Page<User> findByRoleAdmin(Pageable pageable);

    User saveWithImage(User user, MultipartFile image) throws IOException;
}
