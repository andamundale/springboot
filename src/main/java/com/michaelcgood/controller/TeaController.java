package com.michaelcgood.controller;

import com.michaelcgood.mapper.CustomerMapper;
import com.michaelcgood.mapper.TeaMapper;
import com.michaelcgood.model.Customer;
import com.michaelcgood.model.Tea;
import com.michaelcgood.model.dto.CustomerDto;
import com.michaelcgood.model.dto.TeaDto;
import com.michaelcgood.service.TeaService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="tea/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
public class TeaController {

    private TeaService teaService;

    TeaController(TeaService teaService) {
        this.teaService = teaService;
    }

    @PostMapping
    public Tea addTea(Tea tea) {
        return teaService.addTea(tea);
    }

    @GetMapping(value = "/{id}")
    public Tea getTeaById(@PathVariable("id") Long id) {
        if (id >= 0) {
            return teaService.getTeaById(id);
        } else {
            return null;
        }
    }

    @DeleteMapping(value = "{id}")
    public Tea deleteTeaById(@PathVariable("id") Long id) {
        // nejaka pekna podmienka ze vstup je validny
        if (id >= 0) {
            return teaService.deleteTeaById(id);
        } else {
            return null;
        }
    }

    @PutMapping
    public Tea updateTea(@RequestBody Tea tea) {
        // nejaka pekna podmienka ze vstup je validny
        if (tea != null) {
            return teaService.updateTea(tea);
        } else {
            return null;
        }
    }

    @GetMapping(value = "/getCustomersFavouringTea/{id}")
    public List<CustomerDto> getTeasCustomersByTeaId(@PathVariable("id") Long id) {
        List<CustomerDto> result = new ArrayList<CustomerDto>();
        // nejaka pekna podmienka ze vstup je validny
        if (id >= 0) {
            for (Customer customer: teaService.getTeasCustomersByTeaId(id)){
                result.add(CustomerMapper.INSTANCE.customerToCustomerDto(customer));
            }
            return result;
        } else {
            return new ArrayList<CustomerDto>();
        }
    }

    @GetMapping
    public List<TeaDto> getAllTeas() {
        List<TeaDto> result = new ArrayList<TeaDto>();
        for (Tea tea: teaService.getAllTeas()) {
            result.add(TeaMapper.INSTANCE.teaToTeaDto(tea));
        }
        return result;
    };

}
