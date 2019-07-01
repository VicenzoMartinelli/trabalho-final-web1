package br.edu.utfpr.pb.trabalhofinalweb1.service;

import br.edu.utfpr.pb.trabalhofinalweb1.model.ProviderOrder;

public interface IServiceProviderOrder extends IServiceCrud<ProviderOrder, Long> {
    ProviderOrder setDeliveredAndSave(Long id);
}
