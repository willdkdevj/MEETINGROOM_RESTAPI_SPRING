package br.com.supernova.backroom.builder;

import br.com.supernova.backroom.model.dto.RoomDTO;
import lombok.Builder;

@Builder
public class RoomDTOBuilder {

    @Builder.Default
    private static final Long id = 1L;

    @Builder.Default
    private static final String name = "WAR ROOM TEST";

    @Builder.Default
    private static final String date = "28-05-2021";

    @Builder.Default
    private static final String startHour = "09:00";

    @Builder.Default
    private static final String endHour = "13:00";

    public RoomDTO toRoomDTO(){
        return new RoomDTO(id, name, date, startHour, endHour);
    }
}
