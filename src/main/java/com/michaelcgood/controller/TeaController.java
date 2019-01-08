package com.michaelcgood.controller;

//import com.michaelcgood.mapper.CustomerMapper;
//import com.michaelcgood.mapper.TeaMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.michaelcgood.model.Customer;
import com.michaelcgood.model.Tea;
import com.michaelcgood.model.dto.CustomerDto;
import com.michaelcgood.model.dto.TeaDto;
import com.michaelcgood.service.TeaService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="tea/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
public class TeaController {

    private ObjectMapper objectMapper;

    private TeaService teaService;

    TeaController(TeaService teaService) {
        this.teaService = teaService;
    }

    @PostMapping
    public TeaDto addTea(@RequestBody TeaDto teaDto) {
        //return TeaMapper.INSTANCE.teaToTeaDto(teaService.addTea(TeaMapper.INSTANCE.teaDtoToTea(teaDto)));
        return teaService.addTea(teaDto.convertToEntity()).convertToDto();
    }

    @GetMapping(value = "/{id}")
    public TeaDto getTeaById(@PathVariable("id") Long id) {
        if (id >= 0) {
            return teaService.getTeaById(id).convertToDto();
        } else {
            return null;
        }
    }

    @DeleteMapping(value = "{id}")
    public TeaDto deleteTeaById(@PathVariable("id") Long id) {
        TeaDto result;
        // nejaka pekna podmienka ze vstup je validny
        if (id >= 0) {
            return teaService.deleteTeaById(id).convertToDto();
        } else {
            return null;
        }
    }

    @PutMapping
    public TeaDto updateTea(@RequestBody Tea tea) {
        // nejaka pekna podmienka ze vstup je validny
        if (tea != null) {
            return teaService.updateTea(tea).convertToDto();
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
                result.add(customer.convertToDto());
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
            result.add(tea.convertToDto());
        }

        return result;
    };

    @GetMapping(value = "/getAllTeasAsJson/")
    public List<String> getAllTeasAsJsonAsString() throws JsonProcessingException, IOException {
        List<String> result = new ArrayList<String>();
        //String tmpString;
        //Json tmpJson;
        objectMapper = new ObjectMapper();
        for (Tea tea: teaService.getAllTeas()) {
            //objectMapper.writeValue(new File("tmpJson.json"), tea);
            //tmpString = objectMapper.writeValueAsString(tea);
            //result.add(tmpString);
            result .add(objectMapper.writeValueAsString(tea));
        }

        return result;
    }

}
