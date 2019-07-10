package br.edu.utfpr.pb.trabalhofinalweb1.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.edu.utfpr.pb.trabalhofinalweb1.repository.RepositoryOrder;
import br.edu.utfpr.pb.trabalhofinalweb1.viewmodel.ChartsResponse;
import br.edu.utfpr.pb.trabalhofinalweb1.viewmodel.Dataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChartsService {

    @Autowired
    private RepositoryOrder repositoryOrder;

    public ChartsResponse getDataOrdersPerDay(){
        ChartsResponse chartsResponse = new ChartsResponse();
        List<String> lables = new ArrayList<>();
        List<Dataset> datasets = new ArrayList<>();

        chartsResponse.setAppName("Pedidos recebidos por dia - Últimos 30 dias");

        final Dataset dataset = new Dataset();
        dataset.setValue(new ArrayList<>());

        repositoryOrder.findOrdersGroupByDayLast30Days().forEach((x) -> {
            lables.add((String) x[0]);
            dataset.getValue().add((int) x[1]);
        });

        chartsResponse.setLables(lables);
        dataset.setName("Quantidade diária");
        datasets.add(dataset);
        chartsResponse.setDatasets(datasets);

        return chartsResponse;
    }

    public ChartsResponse getDataCountProductsSellPerCategory(){
        ChartsResponse chartsResponse = new ChartsResponse();
        List<String> lables = new ArrayList<>();
        List<Dataset> datasets = new ArrayList<>();

        chartsResponse.setAppName("Nº de produtos vendidos por categoria");

        final Dataset dataset = new Dataset();
        dataset.setValue(new ArrayList<>());

        repositoryOrder.findCountOfSellProductsPerCategories().forEach((x) -> {
            lables.add((String) x[0]);
            dataset.getValue().add((int) x[1]);
        });

        chartsResponse.setLables(lables);
        dataset.setName("Nº de produtos");
        datasets.add(dataset);
        chartsResponse.setDatasets(datasets);

        return chartsResponse;
    }

}