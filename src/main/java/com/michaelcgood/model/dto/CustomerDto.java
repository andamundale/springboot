package com.michaelcgood.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.michaelcgood.model.Tea;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class CustomerDto {
    @Id
    //@JsonIgnore
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @JsonIgnore
    private List<Tea> favouriteTeas;  // zakaznikove oblubene caje}
}