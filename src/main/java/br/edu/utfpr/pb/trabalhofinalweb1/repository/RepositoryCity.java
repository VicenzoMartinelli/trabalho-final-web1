package br.edu.utfpr.pb.trabalhofinalweb1.repository;

import br.edu.utfpr.pb.trabalhofinalweb1.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositoryCity extends JpaRepository<City, Integer> {
    List<City> findByOrderByNameAsc();
}