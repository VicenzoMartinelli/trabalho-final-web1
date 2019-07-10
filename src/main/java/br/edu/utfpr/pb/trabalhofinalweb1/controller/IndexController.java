package br.edu.utfpr.pb.trabalhofinalweb1.controller;

import br.edu.utfpr.pb.trabalhofinalweb1.service.IServiceProduct;
import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ChartsService;
import br.edu.utfpr.pb.trabalhofinalweb1.viewmodel.ChartsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private ChartsService chartService;

    @GetMapping(value = "home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("index");

        return modelAndView;
    }

    @GetMapping(value = "admin")
    public ModelAndView admin() {
        ModelAndView modelAndView = new ModelAndView("admin");

        return modelAndView;
    }


    @GetMapping(value = "")
    public String index(Model model) {

        return "redirect:/home";
    }

    @GetMapping("/viewCharts")
    public ResponseEntity<?> getCharts(){
        List<ChartsResponse> chartsResponse = new ArrayList<>();
        chartsResponse.add(chartService.getDataOrdersPerDay());
        chartsResponse.add(chartService.getDataCountProductsSellPerCategory());

        return new ResponseEntity<>(chartsResponse, HttpStatus.OK);
    }

}