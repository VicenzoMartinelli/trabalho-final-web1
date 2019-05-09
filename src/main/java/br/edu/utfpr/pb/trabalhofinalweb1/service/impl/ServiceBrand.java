package br.edu.utfpr.pb.trabalhofinalweb1.service.impl;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Brand;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryBrand;
import br.edu.utfpr.pb.trabalhofinalweb1.service.IServiceBrand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ServiceBrand extends ServiceCrud<Brand, Integer>
        implements IServiceBrand {

    @Autowired
    private RepositoryBrand brandRepository;

    @Override
    protected JpaRepository<Brand, Integer> getRepository() {
        return brandRepository;
    }

}
