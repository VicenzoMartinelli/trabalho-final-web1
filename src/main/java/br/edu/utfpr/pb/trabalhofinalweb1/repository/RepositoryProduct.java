package br.edu.utfpr.pb.trabalhofinalweb1.repository;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryProduct extends JpaRepository<Product, Integer> {

    Page<Product> findAllByOrderByNameAsc(Pageable pageable);

    Page<Product> findAllByCategoryIdOrderByValueDesc(Integer id, Pageable pageable);

    Page<Product> findAllByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByValueDesc(String filter,String filter2, Pageable pageable);

    Page<Product> findAllByCategoryIdAndNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByValueDesc(Integer id, String filter,String filter2,Pageable pageable);
}