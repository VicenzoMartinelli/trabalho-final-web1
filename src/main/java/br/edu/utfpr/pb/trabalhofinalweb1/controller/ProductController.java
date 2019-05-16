package br.edu.utfpr.pb.trabalhofinalweb1.controller;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Provider;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ServiceCrud;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ServiceProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("product")
public class ProductController extends CrudController<Provider, Integer> {

    @Autowired
    private ServiceProduct _serviceProduct;

    @Override
    protected ServiceCrud getService() {
        return _serviceProduct;
    }

    @Override
    protected String getUrl() {
        return "product";
    }

    @Override
    protected void addDependenciesObjects(ModelAndView model) {

    }

    @Override
    protected Page<Provider> getCustomPaginated() {
        return null;
    }

    @Override
    protected String getPageName() {
        return "Produtos disponíveis";
    }
}
