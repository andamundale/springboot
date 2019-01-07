package com.michaelcgood.app.mapper;

import com.michaelcgood.mapper.CustomerMapper;
import com.michaelcgood.model.Customer;
import com.michaelcgood.model.dto.CustomerDto;
import org.junit.Test;

import java.util.List;

//import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomerMapperTest {

    @Test
    public void customerToCustomerDtoTest() {
        //given
        Customer customer = new Customer( "Alexander", "Velky" );

        //when
        CustomerDto customerDto = CustomerMapper.INSTANCE.customerToCustomerDto( customer );

        //then
        assertNotNull(customerDto);
        assertThat( customerDto.getFirstName(), is( "Alexander" ));
        assertThat( customerDto.getLastName(), is( "Velky" ));
//        System.out.println("test");
    }

    @Test
    public void customerDtoToCustomerTest() {
        CustomerDto customerDto = new CustomerDto("Alexander", "Velky");

        //when
        Customer customer = CustomerMapper.INSTANCE.customerDtoToCustomer( customerDto );

        //then
        assertNotNull(customer);
        assertThat( customer.getFirstName(), is( "Alexander" ));
        assertThat( customer.getLastName(), is( "Velky" ));
//        System.out.println("test");
    }
}
