package com.michaelcgood.model;

import com.michaelcgood.model.dto.TeaDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Tea")
@Data
@NoArgsConstructor
public class Tea {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;
    private String typeOfTea;
    private String countryOfOrigin;

    @ManyToMany(mappedBy = "favouriteTeas")
    @Setter @Getter private List<Customer> customers; // zoznam zakaznikov oblubijucich tento caj

//    TODO not necessary constructor - check lombock @NoArgsConstructor
////      it seems that mapper wants empty constructor before lombok generates one
//    protected Tea() {}

    public Tea(String name, String typeOfTea, String countryOfOrigin) {
        this.id = new Long(0);
        this.name = name;
        this.typeOfTea = typeOfTea;
        this.countryOfOrigin = countryOfOrigin;
        this.customers = new ArrayList<Customer>();
    }

    public Tea(String name, String typeOfTea, String countryOfOrigin, List<Customer> customers) {
        this.id = new Long(0);
        this.name = name;
        this.typeOfTea = typeOfTea;
        this.countryOfOrigin = countryOfOrigin;
        this.customers = customers;
    }

    @Override
    public String toString() {
        return String.format(
                "Tea[id=%d, name='%s', typeOfTea='%s', countryOfOrigin='%s']",
                id, name, typeOfTea, countryOfOrigin);
    }

    public TeaDto convertToDto() {
        return new TeaDto(this.name, this.typeOfTea, this.countryOfOrigin);
    }
}
