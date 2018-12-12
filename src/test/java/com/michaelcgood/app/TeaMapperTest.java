package com.michaelcgood.app;

import com.michaelcgood.mapper.CustomerMapper;
import com.michaelcgood.mapper.TeaMapper;
import com.michaelcgood.model.Customer;
import com.michaelcgood.model.Tea;
import com.michaelcgood.model.dto.CustomerDto;
import com.michaelcgood.model.dto.TeaDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class TeaMapperTest {
    @Test
    public void shouldMapCustomerToDto() {
        //given
        Tea tea = new Tea( "Sencha", "green", "Japan" );

        //when
        TeaDto teaDto = TeaMapper.INSTANCE.teaToTeaDto( tea );

        //then
        assertThat( teaDto ).isNotNull();
        assertThat( teaDto.getName() ).isEqualTo( "Sencha" );
        assertThat( teaDto.getTypeOfTea() ).isEqualTo( "green" );
        assertThat( teaDto.getCountryOfOrigin() ).isEqualTo( "Japan" );
    }
}
