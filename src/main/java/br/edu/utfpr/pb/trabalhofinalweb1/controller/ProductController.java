package br.edu.utfpr.pb.trabalhofinalweb1.controller;

import br.edu.utfpr.pb.trabalhofinalweb1.config.Constants;
import br.edu.utfpr.pb.trabalhofinalweb1.model.Product;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryBrand;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryCategory;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryProduct;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ServiceCrud;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ServiceProduct;
import br.edu.utfpr.pb.trabalhofinalweb1.viewmodel.ProductDTO;
import br.edu.utfpr.pb.trabalhofinalweb1.viewmodel.ProductListViewModel;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.activation.FileTypeMap;
import javax.servlet.ServletRequest;
import javax.validation.Valid;
import javax.xml.ws.Response;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @GetMapping("shop/{categoryId}")
    public ModelAndView list(
            @PathVariable("categoryId") Integer categoryId,
            @RequestParam("filterText") Optional<String> filterText,
            ServletRequest request
    ) {
        ModelAndView md = new ModelAndView(this.getUrl() + "/shopping-products");

        md.addObject("pageName", categoryId != 0 ?
                "Coleção " + _repositoryCategory.findNameById(categoryId)
                        + " ALLIANZ " + LocalDate.now().getMonth()
                            .getDisplayName(TextStyle.FULL, Locale.getDefault())
                        + " " + LocalDate.now().getYear() :
                getPageName());

        md.addObject("categoryId", categoryId == 0 ? null : categoryId);
        md.addObject("filterText", filterText.orElse(null));

        addDependenciesObjects(md);

        return md;
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
        File img = new File(Constants.STORAGE_PATH_PRODUCTS + imageIdentifier);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img)))
                .body(Files.readAllBytes(img.toPath()));
    }

    @GetMapping(value = "defaultimage/{productId}")
    public ResponseEntity<byte[]> defaultimage(@PathVariable Integer productId) throws IOException {

        Product p = _serviceProduct.findOne(productId);

        if( p.getUrlsImgs().length == 0)
            return new ResponseEntity<>(HttpStatus.OK);

        File img = new File(Constants.STORAGE_PATH_PRODUCTS + p.getUrlsImgs()[0]);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img)))
                .body(Files.readAllBytes(img.toPath()));
    }

    @GetMapping(value = "findproducts")
    public ResponseEntity<?> findProducts(
            @RequestParam("categoryId") Optional<Integer> categoryId,
            @RequestParam("filterText") Optional<String> filterText,
            @RequestParam("page") Optional<Integer> page
    )
    {
        int currentPage = page.orElse(1);
        int pageSize = 6;

        PageRequest pageable = PageRequest.of(currentPage - 1, pageSize);

        Page<Product> list = categoryId.isPresent() ? filterText.isPresent() && !filterText.get().isEmpty()  ?
                _serviceProduct.findAllByCategoryIdAndNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByValueDesc(categoryId.get(), filterText.get(), pageable)
                    :  _serviceProduct.findAllByCategoryIdOrderByValueDesc(categoryId.get(), pageable)
                : filterText.isPresent() && !filterText.get().isEmpty() ? _serviceProduct.findAllByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByValueDesc(filterText.get(), pageable)
                    : _serviceProduct.findAll(pageable);

        ProductListViewModel ret = new ProductListViewModel();

        ret.setProducts(list);

        if (list.getTotalPages() != 0) {
            List<Integer> pageNumbers = IntStream
                    .rangeClosed(1, list.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());

            ret.setPageNumbers(pageNumbers);
        }
        ret.setTotalCount(list.getTotalElements());

        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @GetMapping("shop/details/{productId}")
    public ModelAndView productDetails(@PathVariable("productId") Integer productId) {
        ModelAndView md = new ModelAndView(this.getUrl() + "/details");

        md.addObject("product", _serviceProduct.findOne(productId));

        return md;
    }
}
