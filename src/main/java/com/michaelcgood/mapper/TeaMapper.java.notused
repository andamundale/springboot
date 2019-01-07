package com.michaelcgood.mapper;

import com.michaelcgood.model.Tea;
import com.michaelcgood.model.dto.TeaDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeaMapper {
    TeaMapper INSTANCE = Mappers.getMapper(TeaMapper.class);

//    @Mappings({
//            @Mapping(target = "id", ignore = true),
//            @Mapping(target = "customers", ignore = true)
//    })
    TeaDto teaToTeaDto(Tea tea);
    Tea teaDtoToTea(TeaDto teaDto);
}
