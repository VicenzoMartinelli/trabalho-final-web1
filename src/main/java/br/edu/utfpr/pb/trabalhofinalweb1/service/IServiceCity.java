package br.edu.utfpr.pb.trabalhofinalweb1.service;

import br.edu.utfpr.pb.trabalhofinalweb1.model.City;
import br.edu.utfpr.pb.trabalhofinalweb1.viewmodel.CityRegionViewModel;

import java.util.List;

public interface IServiceCity extends IServiceCrud<City, Integer> {

    List<CityRegionViewModel> getDataModel();
}
