package com.michaelcgood.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Tea")
public class Tea {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Getter private Long id;
    @Getter @Setter private String name;
    @Getter @Setter private String typeOfTea;
    @Getter @Setter private String countryOfOrigin;
    @ManyToMany(mappedBy = "favouriteTeas")
    @Getter @Setter private List<Customer> customers;

    protected Tea() {}

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
