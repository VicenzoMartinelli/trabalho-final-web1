package br.edu.utfpr.pb.trabalhofinalweb1.repository;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositoryBrand extends JpaRepository<Brand, Integer> {
    List<Brand> findByOrderByNameAsc();
}