package br.com.supernova.backroom.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "Room")
@Table(name = "tbl_room")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "start_hour", nullable = false)
    private String startHour;

    @Column(name = "end_hour", nullable = false)
    private String endHour;

    @Override
    public String toString(){
        return "[ID= " + getId() + ", Name= " + getName() + ", Date= " + getDate() + ", Start Hour= " + getStartHour() + " and End Hour= " + getEndHour() + "]";
    }
}
