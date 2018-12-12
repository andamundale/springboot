package com.michaelcgood.model;

import lombok.*;

import javax.persistence.*;
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
    private List<Customer> customers; // zoznam zakaznikov oblubijucich tento caj

//    TODO not necessary constructor - check lombock @NoArgsConstructor
////      it seems that mapper wants empty constructor before lombok generates one
//    protected Tea() {}

    public Tea(String name, String typeOfTea, String countryOfOrigin) {
        this.name = name;
        this.typeOfTea = typeOfTea;
        this.countryOfOrigin = countryOfOrigin;
    }

    @Override
    public String toString() {
        return String.format(
                "Tea[id=%d, name='%s', typeOfTea='%s', countryOfOrigin='%s']",
                id, name, typeOfTea, countryOfOrigin);
    }
}
