package com.mantovani.park_api.web.dto.mapper;

import com.mantovani.park_api.entity.Vaga;
import com.mantovani.park_api.web.dto.VagaCreateDto;
import com.mantovani.park_api.web.dto.VagasResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VagaMapper {
    public static Vaga toVaga(VagaCreateDto dto){
        return new ModelMapper().map(dto, Vaga.class);
    }

    public static VagasResponseDto toDto(Vaga vaga){
        return new ModelMapper().map(vaga, VagasResponseDto.class);
    }
}

