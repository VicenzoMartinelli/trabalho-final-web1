package br.edu.utfpr.pb.trabalhofinalweb1.service.impl;

import br.edu.utfpr.pb.trabalhofinalweb1.model.ProviderOrder;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryProviderOrder;
import br.edu.utfpr.pb.trabalhofinalweb1.service.IServiceProviderOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ServiceProviderOrder extends ServiceCrud<ProviderOrder, Long>
        implements IServiceProviderOrder {

    @Autowired
    private RepositoryProviderOrder orderRepository;

    @Override
    protected JpaRepository<ProviderOrder, Long> getRepository() {
        return orderRepository;
    }

}
