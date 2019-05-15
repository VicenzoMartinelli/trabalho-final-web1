package br.edu.utfpr.pb.trabalhofinalweb1.service.impl;

import br.edu.utfpr.pb.trabalhofinalweb1.model.City;
import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryCity;
import br.edu.utfpr.pb.trabalhofinalweb1.service.IServiceCity;
import br.edu.utfpr.pb.trabalhofinalweb1.viewmodel.CityRegionViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceCity extends ServiceCrud<City, Integer>
        implements IServiceCity {

    @Autowired
    private RepositoryCity cityRepository;

    @Override
    protected JpaRepository<City, Integer> getRepository() {
        return cityRepository;
    }

    @Override
    public List<CityRegionViewModel> getDataModel() {
        return cityRepository.findByOrderByNameAsc()
                .stream()
                .map((x) -> new CityRegionViewModel(x.getId(), x.getName(), x.getRegion().getName()))
                .collect(Collectors.toList());
    }
}
