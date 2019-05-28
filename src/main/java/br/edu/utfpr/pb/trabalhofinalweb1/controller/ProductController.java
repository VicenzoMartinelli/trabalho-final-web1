package br.edu.utfpr.pb.trabalhofinalweb1.controller;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Product;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryBrand;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryCategory;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryProduct;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ServiceCrud;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ServiceProduct;
import br.edu.utfpr.pb.trabalhofinalweb1.viewmodel.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.activation.FileTypeMap;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
@RequestMapping("product")
public class ProductController extends CrudController<Product, Integer> {

    @Autowired
    private ServiceProduct _serviceProduct;
    @Autowired
    private RepositoryProduct _repositoryProduct;
    @Autowired
    private RepositoryBrand _repositoryBrand;
    @Autowired
    private RepositoryCategory _repositoryCategory;

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
        model.addObject("brands", _repositoryBrand.findByOrderByNameAsc());
        model.addObject("categories", _repositoryCategory.findByOrderByNameAsc());
    }

    @Override
    protected Page<Product> getCustomPaginated(Pageable pageable) {
        return _repositoryProduct.findAllByOrderByNameAsc(pageable);
    }

    @Override
    protected String getPageName() {
        return "Produtos disponíveis";
    }

    @PostMapping(value = "addproduct")
    public ResponseEntity<?> save(
            @ModelAttribute @Valid ProductDTO model,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        try {
            _serviceProduct.saveWithImages(model);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "findImage/{imageIdentifier}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageIdentifier) throws IOException {
        File img = new File("C://Uploads//Products//" + imageIdentifier);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img)))
                .body(Files.readAllBytes(img.toPath()));
    }
}
