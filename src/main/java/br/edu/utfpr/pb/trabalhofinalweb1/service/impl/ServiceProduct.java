package br.edu.utfpr.pb.trabalhofinalweb1.service.impl;

import br.edu.utfpr.pb.trabalhofinalweb1.config.Constants;
import br.edu.utfpr.pb.trabalhofinalweb1.model.Product;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryProduct;
import br.edu.utfpr.pb.trabalhofinalweb1.service.IServiceProduct;
import br.edu.utfpr.pb.trabalhofinalweb1.viewmodel.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceProduct extends ServiceCrud<Product, Integer>
        implements IServiceProduct {

    @Autowired
    private RepositoryProduct productRepository;

    @Override
    protected JpaRepository<Product, Integer> getRepository() {
        return productRepository;
    }

    @Override
    @Transactional(readOnly = false)
    public Product saveWithImages(ProductDTO model) throws IOException {
        List<String> oldUrls;

        if (model.getId() == null)
            oldUrls = new ArrayList<>();
        else
            oldUrls = new ArrayList<>(Arrays.asList(super.findOne(model.getId()).getUrlsImgs()));

        save(model.getDomain());

        List<String> sourceImgs = new ArrayList<>(
                Arrays.stream(model.getFile())
                        .map((x) -> getFileIdentifier(x, model.getDomain().getId()))
                        .collect(Collectors.toList())
        );

        List<String> imgsToInclude = sourceImgs.stream().collect(Collectors.toList());

        imgsToInclude.removeAll(oldUrls);

        oldUrls.removeAll(sourceImgs);

        for (String path : oldUrls) {
            Files.deleteIfExists(Paths.get(Constants.STORAGE_PATH_PRODUCTS + path));
        }

        for (MultipartFile file : Arrays.stream(model.getFile())
                .filter((x) -> imgsToInclude.stream()
                        .anyMatch((img) -> img.equals(getFileIdentifier(x, model.getDomain().getId())))
                ).collect(Collectors.toList())) {

            Path path = Paths.get(Constants.STORAGE_PATH_PRODUCTS + getFileIdentifier(file, model.getDomain().getId()));
            Files.write(path, file.getBytes());
        }

        model.getDomain().setUrlsImgs(sourceImgs.stream().toArray(String[]::new));

        save(model.getDomain());

        return model.getDomain();
    }

    @Override
    @Transactional(readOnly = true)
    public Product findOne(Integer integer) {
        Product p = super.findOne(integer);

        p.setUrlsImgs(Arrays
                .stream(p.getUrlsImgs())
                .map(x -> (Constants.PRODUCT_SEARCH_IMAGE + x))
                .toArray(String[]::new));

        return p;
    }

    private String getFileIdentifier(MultipartFile x, Integer id) {
        return id + "_" + x.getSize() + "_" + x.getOriginalFilename();
    }
}
