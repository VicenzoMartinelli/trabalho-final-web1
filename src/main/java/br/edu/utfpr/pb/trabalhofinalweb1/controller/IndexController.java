package br.edu.utfpr.pb.trabalhofinalweb1.controller;

import br.edu.utfpr.pb.trabalhofinalweb1.service.IServiceProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @Autowired
    private IServiceProduct _serviceProduct;

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

}