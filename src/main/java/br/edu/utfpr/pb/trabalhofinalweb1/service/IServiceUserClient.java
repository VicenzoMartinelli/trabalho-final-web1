package br.edu.utfpr.pb.trabalhofinalweb1.service;

import br.edu.utfpr.pb.trabalhofinalweb1.model.UserClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IServiceUserClient extends IServiceCrud<UserClient, Long> {

    UserClient saveWithImage(UserClient user, MultipartFile image) throws IOException;

    UserClient findByUsername(String username);
}
