package br.edu.utfpr.pb.trabalhofinalweb1.service.impl;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Product;
import br.edu.utfpr.pb.trabalhofinalweb1.model.ProviderOrder;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryProviderOrder;
import br.edu.utfpr.pb.trabalhofinalweb1.service.IServiceProviderOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiceProviderOrder extends ServiceCrud<ProviderOrder, Long>
        implements IServiceProviderOrder {

    @Autowired
    private RepositoryProviderOrder orderRepository;

    @Autowired
    private ServiceProduct serviceProduct;

    @Override
    protected JpaRepository<ProviderOrder, Long> getRepository() {
        return orderRepository;
    }

    @Override
    @Transactional(readOnly = false)
    public ProviderOrder save(ProviderOrder entity) {
        entity = super.save(entity);

        if(entity.isDelivered())
        {
            entity.getOrderItems().forEach((item) -> {
                Product p = serviceProduct.findOne(item.getProduct().getId());

                p.setCount(p.getCount() + item.getCount());

                serviceProduct.save(p);
            });
        }

        return entity;
    }

    @Transactional(readOnly = false)
    public ProviderOrder setDeliveredAndSave(Long id) {
        ProviderOrder po = this.findOne(id);

        po.setDelivered(true);

        return this.save(po);
    }

    @Transactional(readOnly = false)
    public ProviderOrder setCanceledAndSave(Long id) {
        ProviderOrder po = this.findOne(id);

        po.setCanceled(true);
        po.setDelivered(false);

        return this.save(po);
    }
}
