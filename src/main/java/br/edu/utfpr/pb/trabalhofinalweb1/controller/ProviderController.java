package br.edu.utfpr.pb.trabalhofinalweb1.controller;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Provider;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ServiceCity;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ServiceCrud;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("provider")
public class ProviderController extends CrudController<Provider, Integer> {

    @Autowired
    private ServiceProvider _serviceProvider;

    @Autowired
    private ServiceCity _serviceCity;

    @Override
    protected ServiceCrud getService() {
        return _serviceProvider;
    }

    @Override
    protected String getUrl() {
        return "provider";
    }

    @Override
    protected void addDependenciesObjects(ModelAndView model) {
        model.addObject("cidades", _serviceCity.getDataModel());
    }

    @Override
    protected Page<Provider> getCustomPaginated(Pageable pageable) {
        return null;
    }

    @Override
    protected String getPageName() {
        return "Fornecedores disponíveis";
    }
}
