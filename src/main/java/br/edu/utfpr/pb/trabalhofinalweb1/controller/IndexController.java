package br.edu.utfpr.pb.trabalhofinalweb1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @GetMapping(value = "home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("index");

        return modelAndView;
    }


    @GetMapping(value = "")
    public String index(Model model) {

        return "redirect:/home";
    }

}