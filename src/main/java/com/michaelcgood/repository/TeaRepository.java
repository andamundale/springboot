package com.michaelcgood.repository;

import com.michaelcgood.model.Tea;
import com.michaelcgood.model.dto.TeaDto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TeaRepository extends CrudRepository<Tea, Long> {

    Optional<Tea> findById(Long id);

    List<TeaDto> findById(List<Long> customers);

    List<TeaDto> findByName(String name);

    List<TeaDto> findByTypeOfTea(String name);

    List<TeaDto> findByCountryOfOrigin(String name);
}
