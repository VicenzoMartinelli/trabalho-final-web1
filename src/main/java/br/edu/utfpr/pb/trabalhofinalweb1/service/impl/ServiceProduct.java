package br.edu.utfpr.pb.trabalhofinalweb1.service.impl;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Product;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryProduct;
import br.edu.utfpr.pb.trabalhofinalweb1.service.IServiceProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ServiceProduct extends ServiceCrud<Product, Integer>
        implements IServiceProduct {

    @Autowired
    private RepositoryProduct productRepository;

    @Override
    protected JpaRepository<Product, Integer> getRepository() {
        return productRepository;
    }

}
