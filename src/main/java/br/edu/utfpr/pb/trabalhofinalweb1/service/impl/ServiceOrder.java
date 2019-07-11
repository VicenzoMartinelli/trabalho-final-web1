package br.edu.utfpr.pb.trabalhofinalweb1.service.impl;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Order;
import br.edu.utfpr.pb.trabalhofinalweb1.model.Product;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryOrder;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryProduct;
import br.edu.utfpr.pb.trabalhofinalweb1.service.IServiceOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiceOrder extends ServiceCrud<Order, Long>
        implements IServiceOrder {

    @Autowired
    private RepositoryOrder orderRepository;
    @Autowired
    private RepositoryProduct productRepository;

    @Override
    protected JpaRepository<Order, Long> getRepository() {
        return orderRepository;
    }

    public Page<Order> findAllByUserClientUsernameOrderByOrderDateDesc(Pageable pageable, String name) {
        return orderRepository.findAllByUserClientUsernameOrderByOrderDateDesc(pageable, name);
    }

    @Override
    @Transactional(readOnly = false)
    public Order save(Order entity) {
        Order saved = super.save(entity);

        saved.getOrderItems().forEach((orderItem -> {
            Product p = productRepository.getOne(orderItem.getProduct().getId());

            p.setCount(p.getCount() - orderItem.getCount());

            productRepository.save(p);
        }));

        return saved;
    }
}
