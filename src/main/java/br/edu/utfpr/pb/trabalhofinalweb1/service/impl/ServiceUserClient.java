package br.edu.utfpr.pb.trabalhofinalweb1.service.impl;

import br.edu.utfpr.pb.trabalhofinalweb1.config.Constants;
import br.edu.utfpr.pb.trabalhofinalweb1.model.Role;
import br.edu.utfpr.pb.trabalhofinalweb1.model.User;
import br.edu.utfpr.pb.trabalhofinalweb1.model.UserClient;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryRole;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryUser;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryUserClient;
import br.edu.utfpr.pb.trabalhofinalweb1.service.IServiceUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Service
public class ServiceUserClient extends ServiceCrud<UserClient, Long>
        implements IServiceUserClient, UserDetailsService {

    @Autowired
    private RepositoryUserClient userClientRepository;

    @Autowired
    private RepositoryUser userRepository;

    @Autowired
    private RepositoryRole roleRepository;

    @Override
    public UserClient saveWithImage(UserClient user, MultipartFile image) throws IOException {
        Optional<String> newImg = null;
        Boolean sameImage = false;

        if (user.getId() == null) {
            user.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName("ROLE_USER"))));

            user.setPassword(user.getEncondedPassword());
        } else {
            user.setRoles(userRepository.findRolesById(user.getId()));

            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                user.setPassword(userRepository.findPasswordById(user.getId()));
            } else {
                user.setPassword(user.getEncondedPassword());
            }
        }

        save(user);

        newImg = image == null ? Optional.empty() :
                Optional.ofNullable(getFileIdentifier(user, image));

        sameImage = newImg.orElse("").equals(user.getImageUrl());

        if (!sameImage) {
            if (user.getImageUrl() != null && !user.getImageUrl().isEmpty())
                Files.deleteIfExists(Paths.get(Constants.STORAGE_PATH_USERS + user.getImageUrl()));

            if (newImg.isPresent() && !newImg.get().isEmpty()) {
                Files.write(
                        Paths.get(Constants.STORAGE_PATH_USERS + newImg.get()),
                        image.getBytes());

                user.setImageUrl(newImg.get());
            }

        }

        save(user);

        return user;
    }

    @Override
    public UserClient findByUsername(String username) {
        return userClientRepository.findByUsername(username);
    }

    @Override
    protected JpaRepository<UserClient, Long> getRepository() {
        return userClientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserClient user = findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("Falha ao encontrar usuário!");
        }
        return user;
    }
    private String getFileIdentifier(User user, MultipartFile image) {
        return user.getId() + "_" + image.getSize() + "_" + image.getOriginalFilename();
    }
}
