package br.edu.utfpr.pb.trabalhofinalweb1.controller;

import br.edu.utfpr.pb.trabalhofinalweb1.model.User;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryUser;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ServiceCrud;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("account")
public class AccountController extends CrudController<User, Long> {

    @Autowired
    private ServiceUser _service;

    @Autowired
    private RepositoryUser _repository;

    @Override
    protected ServiceCrud getService() {
        return _service;
    }

    @Override
    protected String getUrl() {
        return "account";
    }

    @Override
    protected void addDependenciesObjects(ModelAndView model) {

    }

    @Override
    protected Page getCustomPaginated(Pageable pageable) {
        //return _service.findByRoleAdmin(pageable);
        return null;
    }

    @Override
    protected String getPageName() {
        return "Lista de usuários";
    }
}
