package com.michaelcgood.app.mapper;

import com.michaelcgood.mapper.TeaMapper;
import com.michaelcgood.model.Tea;
import com.michaelcgood.model.dto.TeaDto;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TeaMapperTest {

    @Test
    public void teaToTeaDtoTest() {
        //given
        Tea tea = new Tea( "Sencha", "green tea", "Japan" );

        //when
        TeaDto teaDto = TeaMapper.INSTANCE.teaToTeaDto( tea );

        //then
        assertNotNull(teaDto);
        assertThat( teaDto.getName(), is( "Sencha" ));
        assertThat( teaDto.getTypeOfTea(), is( "green tea" ));
        assertThat( teaDto.getCountryOfOrigin(), is( "Japan" ));
    }

    @Test
    public void teaDtoToteaTest() {
        TeaDto teaDto = new TeaDto();
        teaDto.setName("Sencha");
        teaDto.setTypeOfTea("green tea");
        teaDto.setCountryOfOrigin("Japan");

        //when
        Tea tea = TeaMapper.INSTANCE.teaDtoToTea( teaDto );

        //then
        assertNotNull(tea);
        assertThat( tea.getName(), is( "Sencha" ));
        assertThat( tea.getTypeOfTea(), is( "green tea" ));
        assertThat( tea.getCountryOfOrigin(), is( "Japan" ));
    }
}
