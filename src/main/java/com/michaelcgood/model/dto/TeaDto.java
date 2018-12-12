package com.michaelcgood.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.michaelcgood.model.Customer;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class TeaDto {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    //@JsonIgnore
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String typeOfTea;
    @NotNull
    private String countryOfOrigin;
    @JsonIgnore
    private List<Customer> customers; // zoznam zakaznikov oblubijucich tento caj

}
