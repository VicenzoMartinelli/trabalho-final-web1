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

        User dbUser = userRepository.findById(user.getId()).orElse(new User());


        if (user.getId() == null) {
            dbUser.setRoles(new HashSet<>(Arrays.asList(roleService.findAdminRole())));

            dbUser.setPassword(user.getEncondedPassword());
        } else {
            if (! (user.getPassword() == null || user.getPassword().isEmpty())) {
                user.setPassword(user.getEncondedPassword());
            }
        }

        dbUser.setCpf(user.getCpf());
        dbUser.setEmail(user.getEmail());
        dbUser.setName(user.getName());

        save(dbUser);

        newImg = image == null ? Optional.empty() : Optional.ofNullable(getFileIdentifier(dbUser, image));

        sameImage = newImg.orElse("").equals(dbUser.getImageUrl());

        if (!sameImage) {
            if (dbUser.getImageUrl() != null && !dbUser.getImageUrl().isEmpty())
                Files.deleteIfExists(Paths.get(Constants.STORAGE_PATH_USERS + dbUser.getImageUrl()));

            if (newImg.isPresent() && !newImg.get().isEmpty()) {
                Files.write(
                        Paths.get(Constants.STORAGE_PATH_USERS + newImg.get()),
                        image.getBytes());

                dbUser.setImageUrl(newImg.get());
            }

        }

        save(dbUser);

        return dbUser;
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
