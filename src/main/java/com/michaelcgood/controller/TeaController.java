package com.michaelcgood.controller;

import com.michaelcgood.model.Tea;
import com.michaelcgood.service.TeaService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TeaController {

    private TeaService teaService;

    TeaController(TeaService teaService) {
        this.teaService = teaService;
    }

    @PostMapping(value = "/addTea")
    public Tea addTea(Tea tea) {
        return teaService.addTea(tea);
    }

    @GetMapping(value = "/getTeaByName")
    public List<Tea> getTeaByName(String name) {
        if (name != "") {
            return teaService.getTeaByName(name);
        } else {
            return new ArrayList<Tea>();
        }
    }

    @DeleteMapping(value = "/deleteTeaById")
    public Tea deleteTeaById(Long id) {
        // nejaka pekna podmienka ze vstup je validny
        if (id >= 0) {
            return teaService.deleteTeaById(id);
        } else {
            return null;
        }
    }

    @PutMapping(value = "/updateTea")
    public Tea updateTea(Tea tea) {
        // nejaka pekna podmienka ze vstup je validny
        if (tea != null) {
            return teaService.updateTea(tea);
        } else {
            return null;
        }
    }

    @GetMapping(value = "/getTeasCustomersByTeaId")
    public List<String> getTeasCustomersByTeaId(Long id) {
        // nejaka pekna podmienka ze vstup je validny
        if (id >= 0) {
            return teaService.getTeasCustomersByTeaId(id);
        } else {
            return new ArrayList<String>();
        }
    }

    @GetMapping(
            value = "/getAllTeas",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<String> getAllTeas() {
        return teaService.getAllTeas();
    };

}
