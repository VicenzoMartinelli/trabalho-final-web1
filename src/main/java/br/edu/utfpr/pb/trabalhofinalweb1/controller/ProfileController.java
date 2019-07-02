package br.edu.utfpr.pb.trabalhofinalweb1.controller;

import br.edu.utfpr.pb.trabalhofinalweb1.config.Constants;
import br.edu.utfpr.pb.trabalhofinalweb1.model.User;
import br.edu.utfpr.pb.trabalhofinalweb1.model.UserClient;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryUser;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ServiceCity;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ServiceCrud;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ServiceUser;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ServiceUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.activation.FileTypeMap;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.nio.file.Files;
import java.util.Optional;

@Controller
@RequestMapping("profile")
public class ProfileController extends CrudController<UserClient, Long> {

    @Autowired
    private ServiceUser _service;
    @Autowired
    private ServiceUserClient _serviceUC;

    @Autowired
    private RepositoryUser _repository;

    @Autowired
    private ServiceCity _serviceCity;

    @Override
    protected ServiceCrud getService() {
        return _service;
    }

    @Override
    protected String getUrl() {
        return "profile";
    }

    @Override
    protected void addDependenciesObjects(ModelAndView model) {
        model.addObject("cidades", _serviceCity.getDataModel());
    }

    @Override
    protected Page getCustomPaginated(Pageable pageable) {
        return _repository.findAllByOrderByNameAsc(pageable);
    }

    @Override
    protected String getPageName() {
        return "Perfil";
    }

    @GetMapping("newclientprofile")
    public ModelAndView form(@Nullable Optional<UserClient> entity) {
        ModelAndView md = new ModelAndView(this.getUrl() + "/form-client");

        UserClient uc = entity.isPresent() && entity.get().getId() != null
                ? _serviceUC.findOne(entity.get().getId()) : new UserClient();

        md.addObject("entity", uc);

        addDependenciesObjects(md);

        return md;
    }

    @PostMapping("saveProfile")
    public ResponseEntity<?> saveWithImage(
            @Valid UserClient entity,
            BindingResult result,
            @RequestParam("image") Optional<MultipartFile> image
    ) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        try {
            _service.saveWithImage(entity, image.orElse(null));
        } catch (IOException e) {
            new ResponseEntity<>("Erro ao salvar o usuário!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "findImage/{imageIdentifier}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageIdentifier) throws IOException {
        File img = new File(Constants.STORAGE_PATH_USERS + imageIdentifier);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img)))
                .body(Files.readAllBytes(img.toPath()));
    }
}
