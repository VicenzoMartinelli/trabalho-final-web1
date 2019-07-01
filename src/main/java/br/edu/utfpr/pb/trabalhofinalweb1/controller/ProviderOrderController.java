package br.edu.utfpr.pb.trabalhofinalweb1.controller;

import br.edu.utfpr.pb.trabalhofinalweb1.model.ProviderOrder;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ServiceCrud;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ServiceProduct;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ServiceProvider;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ServiceProviderOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

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

    @PostMapping("saveProviderOrder")
    public ResponseEntity<?> saveJson(@RequestBody @Valid ProviderOrder entity,
                                      BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        _serviceOrder.save(entity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("saveDeliveredOrder/{id}")
    public ResponseEntity<?> saveDelivered(
            @PathVariable("id") Long id){
        if(id != null && id != 0)
        {
            _serviceOrder.setDeliveredAndSave(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("saveCanceledOrder/{id}")
    public ResponseEntity<?> saveCanceled(
            @PathVariable("id") Long id){
        if(id != null && id != 0)
        {
            _serviceOrder.setCanceledAndSave(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
