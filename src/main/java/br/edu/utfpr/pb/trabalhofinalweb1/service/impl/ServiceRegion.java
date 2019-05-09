package br.edu.utfpr.pb.trabalhofinalweb1.service.impl;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Region;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryRegion;
import br.edu.utfpr.pb.trabalhofinalweb1.service.IServiceRegion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ServiceRegion extends ServiceCrud<Region, Integer>
        implements IServiceRegion {

    @Autowired
    private RepositoryRegion regionRepository;

    @Override
    protected JpaRepository<Region, Integer> getRepository() {
        return regionRepository;
    }

}
