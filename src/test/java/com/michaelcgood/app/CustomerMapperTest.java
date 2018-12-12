package com.michaelcgood.app;

import com.michaelcgood.mapper.CustomerMapper;
import com.michaelcgood.model.Customer;
import com.michaelcgood.model.Tea;
import com.michaelcgood.model.dto.CustomerDto;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

public class CustomerMapperTest {
    @Test
    public void shouldMapCustomerToDto() {
        //given
        Customer customer = new Customer( "Alexander", "Velky" );

        //when
        CustomerDto customerDto = CustomerMapper.INSTANCE.customerToCustomerDto( customer );

        //then
        assertThat( customerDto ).isNotNull();
        assertThat( customerDto.getFirstName() ).isEqualTo( "Alexander" );
        assertThat( customerDto.getLastName() ).isEqualTo( "Velky" );
    }
}
