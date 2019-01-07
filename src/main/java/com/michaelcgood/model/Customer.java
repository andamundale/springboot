package com.michaelcgood.model;

import javax.persistence.*;

import com.michaelcgood.model.dto.CustomerDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Customer")
@Data
@NoArgsConstructor
public class Customer {

//    TODO A: code looks messy, check style below or move @Getter @Setter to classlevel

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "favourite_tea",
            joinColumns = @JoinColumn(name = "tea_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"))
    private List<Tea> favouriteTeas;  // zakaznikove oblubene caje

//    TODO not necessary constructor - check lombock @NoArgsConstructor
////      it seems that mapper wants empty constructor before lombok generates one
//    protected Customer() {}


    public Customer(String firstName, String lastName) {
        this.id = new Long(0);
        this.firstName = firstName;
        this.lastName = lastName;
        this.favouriteTeas = new ArrayList<Tea>();
    }

    public Customer(String firstName, String lastName, List<Tea> favouriteTeas) {
        this.id = new Long(0);
        this.firstName = firstName;
        this.lastName = lastName;
        this.favouriteTeas = favouriteTeas;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }

    public CustomerDto convertToDto() {
        return new CustomerDto(this.firstName, this.lastName);
    }

}