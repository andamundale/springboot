package com.michaelcgood.model.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class TeaDto {
    //Long id;
    String name;
    String typeOfTea;
    String countryOfOrigin;
    //List<Customer> customers; // zoznam zakaznikov oblubijucich tento caj

}
