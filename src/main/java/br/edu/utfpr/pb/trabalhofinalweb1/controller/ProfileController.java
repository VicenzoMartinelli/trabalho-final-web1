package br.edu.utfpr.pb.trabalhofinalweb1.controller;

import br.edu.utfpr.pb.trabalhofinalweb1.config.Constants;
import br.edu.utfpr.pb.trabalhofinalweb1.converter.Base64Converter;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @Autowired
    private Base64Converter _cv64;

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

    @Override
    @GetMapping("newclientprofile")
    public ModelAndView form(UserClient entity) {
        ModelAndView md = new ModelAndView(this.getUrl() + "/form-client");

        md.addObject("openType", 0);
        md.addObject("isClient", true);
        addDependenciesObjects(md);

        return md;
    }

    @GetMapping("editprofile/{username}")
    public ModelAndView editprofile(
            @PathVariable("username") String username
    ) {
        if(username == null || username.isEmpty())
            return new ModelAndView("admin");

        if( !SecurityContextHolder.getContext().getAuthentication().isAuthenticated() ||
            !username.equals(SecurityContextHolder.getContext().getAuthentication().getName()))
            return new ModelAndView("redirect:/home?n=1&m=" + _cv64.encode("Que ce tá fazendo ai?"));

        User us = _service.findByUsername(username);
        Boolean isUserClient = us instanceof UserClient;

        ModelAndView md = new ModelAndView(this.getUrl() + (isUserClient ? "/form-client" : "/form"));


        md.addObject("entity", isUserClient ? (UserClient) us : us);
        md.addObject("isClient", isUserClient);
        md.addObject(   "openType", 1);

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
            _serviceUC.saveWithImage(entity, image.orElse(null));
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
