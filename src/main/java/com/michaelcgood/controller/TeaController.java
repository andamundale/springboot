package com.michaelcgood.controller;

import com.michaelcgood.dao.TeaRepository;
import com.michaelcgood.model.Tea;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TeaController {

    private TeaRepository teaRepository;

    TeaController(TeaRepository repository) {
        this.teaRepository = repository;
    }

    @RequestMapping(value = "/addTea", method = { RequestMethod.GET, RequestMethod.POST })
    public void addTea(Tea tea) {
        teaRepository.save(tea);
    }

    @RequestMapping(value = "/getTeaByName", method = { RequestMethod.GET, RequestMethod.POST })
    public List<Tea> getTeaByName(String name) {
        List<Tea> result = new ArrayList<>();
        result = teaRepository.findByName(name);

        return result;
    }

    @RequestMapping(value = "/deleteTeaById", method = { RequestMethod.GET, RequestMethod.POST })
    public boolean deleteTeaById(Long id) {
        boolean result = false;
        if (teaRepository.existsById(id)) {
            result = true;
            teaRepository.deleteById(id);
        }

        return result;
    }

    @RequestMapping(value = "/updateTeaById", method = { RequestMethod.GET, RequestMethod.POST })
    public boolean updateTeasById(Tea tea) {
        boolean result = false;
        if (teaRepository.existsById(tea.getId())) {
            teaRepository.save(tea);
            result = true;
        }

        return result;
    }
}
