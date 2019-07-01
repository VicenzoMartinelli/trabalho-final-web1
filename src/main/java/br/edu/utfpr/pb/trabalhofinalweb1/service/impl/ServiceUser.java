package br.edu.utfpr.pb.trabalhofinalweb1.service.impl;

import br.edu.utfpr.pb.trabalhofinalweb1.config.Constants;
import br.edu.utfpr.pb.trabalhofinalweb1.model.User;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryUser;
import br.edu.utfpr.pb.trabalhofinalweb1.service.IServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Service
public class ServiceUser extends ServiceCrud<User, Long>
        implements IServiceUser, UserDetailsService {

    @Autowired
    private RepositoryUser userRepository;
    @Autowired
    private ServiceRole roleService;

    @Override
    protected JpaRepository<User, Long> getRepository() {
        return userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User usuario = userRepository.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não "
                    + "encontrado!");
        }
        return usuario;
    }

    @Override
    public Page<User> findByRoleAdmin(org.springframework.data.domain.Pageable pageable) {
        return userRepository.findAllByRolesContainingOrderByNameAsc(
                roleService.findAdminRole(),
                pageable);
    }

    @Override
    @Transactional(readOnly = false)
    public User saveWithImage(User user, MultipartFile image) throws IOException {
        Optional<String> newImg = null;
        Boolean sameImage = false;

        if (user.getId() == null) {
            user.setRoles(new HashSet<>(Arrays.asList(roleService.findAdminRole())));

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

        newImg = image == null ? Optional.empty() : Optional.ofNullable(getFileIdentifier(user, image));

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

    private String getFileIdentifier(User user, MultipartFile image) {
        return user.getId() + "_" + image.getSize() + "_" + image.getOriginalFilename();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
