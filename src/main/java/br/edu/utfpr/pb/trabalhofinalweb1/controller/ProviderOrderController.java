package br.edu.utfpr.pb.trabalhofinalweb1.controller;

import br.edu.utfpr.pb.trabalhofinalweb1.model.ProviderOrder;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("providerorder")
public class ProviderOrderController extends CrudController<ProviderOrder, Long> {

    @Autowired
    private ServiceProviderOrder _serviceOrder;

    @Autowired
    private ServiceProduct _serviceProducts;
    @Autowired
    private ServiceProvider _serviceProvider;

    @Override
    protected ServiceCrud getService() {
        return _serviceOrder;
    }

    @Override
    protected String getUrl() {
        return "providerorder";
    }

    @Override
    protected void addDependenciesObjects(ModelAndView model) {
        model.addObject("products", _serviceProducts.findAll());
        model.addObject("providers", _serviceProvider.findAll());
    }

    @Override
    protected Page<ProviderOrder> getCustomPaginated(Pageable pageable) {
        return null;
    }

    @Override
    protected String getPageName() {
        return "Pedidos de produtos";
    }
}
