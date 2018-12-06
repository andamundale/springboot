package com.michaelcgood.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Getter private Long id;
    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;
    //@Getter @Setter private List<Tea> teas;  //oblubeny caj alebo caje

    protected Customer() {}

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }

}