package br.com.supernova.backroom.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {

    private Long id;

    @NotEmpty
    @Size(min = 5, max = 70)
    private String name;

    @NotEmpty
    private String date;

    @NotEmpty
    private String startHour;

    @NotEmpty
    private String endHour;

}
