package br.edu.utfpr.pb.trabalhofinalweb1.service.impl;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Provider;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryProvider;
import br.edu.utfpr.pb.trabalhofinalweb1.service.IServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ServiceProvider extends ServiceCrud<Provider, Integer>
        implements IServiceProvider {

    @Autowired
    private RepositoryProvider providerRepository;

    @Override
    protected JpaRepository<Provider, Integer> getRepository() {
        return providerRepository;
    }

}
