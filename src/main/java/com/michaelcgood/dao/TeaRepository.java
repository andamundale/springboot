package com.michaelcgood.dao;

import com.michaelcgood.model.Tea;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeaRepository extends CrudRepository<Tea, Long> {

    List<Tea> findByName(String name);

    List<Tea> findByTypeOfTea(String name);

    List<Tea> findByCountryOfOrigin(String name);
}
