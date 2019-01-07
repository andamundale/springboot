package com.michaelcgood.mapper;

import com.michaelcgood.model.Customer;
import com.michaelcgood.model.dto.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

//    @Mappings({
//            @Mapping(target = "id", ignore = true),
//            @Mapping(target = "favouriteTeas", ignore = true)
//    })
    CustomerDto customerToCustomerDto(Customer customer);
    Customer customerDtoToCustomer(CustomerDto customerDto);
}
