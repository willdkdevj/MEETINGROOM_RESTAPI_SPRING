package br.com.supernova.backroom.mapper;

import br.com.supernova.backroom.model.dto.RoomDTO;
import br.com.supernova.backroom.model.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomMapper {

    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    RoomDTO toDTO(Room room);

    @Mapping(target = "date", source = "date", dateFormat = "dd-MM-yyyy")
    Room toModel(RoomDTO roomDTO);
}
