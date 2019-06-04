package br.edu.utfpr.pb.trabalhofinalweb1.service;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Product;
import br.edu.utfpr.pb.trabalhofinalweb1.viewmodel.ProductDTO;

import java.io.IOException;

public interface IServiceProduct extends IServiceCrud<Product, Integer> {
    Product saveWithImages(ProductDTO dto) throws IOException;
}
