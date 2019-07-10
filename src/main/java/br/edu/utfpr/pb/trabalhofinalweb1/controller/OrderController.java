package br.edu.utfpr.pb.trabalhofinalweb1.controller;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Order;
import br.edu.utfpr.pb.trabalhofinalweb1.model.ProviderOrder;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("order")
public class OrderController extends CrudController<Order, Long> {

    @Autowired
    private ServiceOrder _serviceOrder;

    @Override
    protected ServiceCrud getService() {
        return _serviceOrder;
    }

    @Override
    protected String getUrl() {
        return "order";
    }

    @Override
    protected void addDependenciesObjects(ModelAndView model) {
    }

    @Override
    protected Page<Order> getCustomPaginated(Pageable pageable){
        return _serviceOrder.findAllByUserClientUsernameOrderByOrderDateDesc(pageable, SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Override
    protected String getPageName() {
        return "Pedidos realizados";
    }

    @GetMapping("checkout")
    public ModelAndView productDetails() {
        ModelAndView md = new ModelAndView(this.getUrl() + "/checkout");

        return md;
    }

    @PostMapping("saveorder")
    public ResponseEntity<?> saveJson(@RequestBody @Valid Order entity,
                                      BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Order r = _serviceOrder.save(entity);

        return new ResponseEntity<>(r, HttpStatus.OK);
    }
}
