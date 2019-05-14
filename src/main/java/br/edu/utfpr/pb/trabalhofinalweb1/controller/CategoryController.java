package br.edu.utfpr.pb.trabalhofinalweb1.controller;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Category;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ServiceCategory;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ServiceCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("category")
public class CategoryController extends CrudController<Category, Integer> {

    @Autowired
    private ServiceCategory _service;

    @Override
    protected ServiceCrud getService() {
        return _service;
    }

    @Override
    protected String getUrl() {
        return "category";
    }

    @Override
    protected void addDependenciesObjects(ModelAndView model) {

    }

    @Override
    protected Page<Category> getCustomPaginated() {
        return null;
    }

    @Override
    protected String getPageName() {
        return "Categorias disponíveis";
    }
}
