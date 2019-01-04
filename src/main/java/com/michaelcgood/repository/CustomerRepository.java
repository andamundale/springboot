package com.michaelcgood.repository;

import java.util.List;
import java.util.Optional;

import com.michaelcgood.model.Customer;
import com.michaelcgood.model.dto.CustomerDto;
import org.springframework.data.repository.CrudRepository;

//TODO rename package to repositories (it is more SpringData oriented)
public interface CustomerRepository extends CrudRepository<Customer, Long>  {

    Optional<Customer> findById(Long id);

    List<CustomerDto> findById(List<Long> customers);

    List<CustomerDto> findByLastName(String lastName);

    List<CustomerDto> findByFirstName(String firstName);

    List<Customer> findByFirstNameAndLastName(String firstName, String lastName);
}
