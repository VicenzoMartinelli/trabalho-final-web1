package br.edu.utfpr.pb.trabalhofinalweb1.service.impl;

import br.edu.utfpr.pb.trabalhofinalweb1.model.City;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryCity;
import br.edu.utfpr.pb.trabalhofinalweb1.service.IServiceCity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ServiceCity extends ServiceCrud<City, Integer>
        implements IServiceCity {

    @Autowired
    private RepositoryCity cityRepository;

    @Override
    protected JpaRepository<City, Integer> getRepository() {
        return cityRepository;
    }

}
