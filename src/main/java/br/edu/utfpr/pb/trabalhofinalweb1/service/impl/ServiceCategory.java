package br.edu.utfpr.pb.trabalhofinalweb1.service.impl;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Category;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryCategory;
import br.edu.utfpr.pb.trabalhofinalweb1.service.IServiceCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ServiceCategory extends ServiceCrud<Category, Integer>
        implements IServiceCategory {

    @Autowired
    private RepositoryCategory categoryRepository;

    @Override
    protected JpaRepository<Category, Integer> getRepository() {
        return categoryRepository;
    }

}
