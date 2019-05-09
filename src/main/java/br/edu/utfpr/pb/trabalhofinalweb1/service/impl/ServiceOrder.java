package br.edu.utfpr.pb.trabalhofinalweb1.service.impl;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Order;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryOrder;
import br.edu.utfpr.pb.trabalhofinalweb1.service.IServiceOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ServiceOrder extends ServiceCrud<Order, Long>
        implements IServiceOrder {

    @Autowired
    private RepositoryOrder orderRepository;

    @Override
    protected JpaRepository<Order, Long> getRepository() {
        return orderRepository;
    }

}
