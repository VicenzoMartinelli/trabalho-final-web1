package br.edu.utfpr.pb.trabalhofinalweb1.repository;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositoryCategory extends JpaRepository<Category, Integer> {
    List<Category> findByOrderByNameAsc();
}