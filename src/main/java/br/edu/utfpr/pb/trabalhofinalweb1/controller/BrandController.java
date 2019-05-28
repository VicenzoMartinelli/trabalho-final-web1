package br.edu.utfpr.pb.trabalhofinalweb1.controller;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Brand;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ServiceBrand;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ServiceCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("brand")
public class BrandController extends CrudController<Brand, Integer> {

    @Autowired
    private ServiceBrand _service;

    @Override
    protected ServiceCrud getService() {
        return _service;
    }

    @Override
    protected String getUrl() {
        return "brand";
    }

    @Override
    protected void addDependenciesObjects(ModelAndView model) {

    }

    @Override
    protected Page<Brand> getCustomPaginated(Pageable pageable) {
        return null;
    }

    @Override
    protected String getPageName() {
        return "Marcas disponíveis";
    }


}
