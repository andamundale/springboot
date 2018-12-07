package com.michaelcgood.dao;

import java.util.List;

import com.michaelcgood.model.Customer;
import org.springframework.data.repository.CrudRepository;

//TODO rename package to repositories (it is more SpringData oriented)
public interface CustomerRepository extends CrudRepository<Customer, Long>  {

    List<Customer> findByLastName(String lastName);
}
