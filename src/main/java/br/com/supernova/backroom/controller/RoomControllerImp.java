package br.com.supernova.backroom.controller;

import br.com.supernova.backroom.model.dto.RoomDTO;
import br.com.supernova.backroom.exception.ResourceNotFoundException;
import br.com.supernova.backroom.exception.ResourceRoomAlreadyRegisteredException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface RoomControllerImp {


    ResponseEntity<List<RoomDTO>> getAllRooms();

    ResponseEntity<RoomDTO> findByID(@PathVariable Long id) throws ResourceNotFoundException;

    ResponseEntity<RoomDTO> findByName(@PathVariable String name) throws ResourceNotFoundException;

    ResponseEntity<RoomDTO> registerRoom(@Valid @RequestBody RoomDTO roomDTO) throws ResourceRoomAlreadyRegisteredException;

    ResponseEntity<RoomDTO> updateRoom(@PathVariable Long id, @Valid @RequestBody RoomDTO roomDTO) throws ResourceNotFoundException, ResourceRoomAlreadyRegisteredException;

    ResponseEntity<Map<String, Boolean>> deleteById(@PathVariable Long id) throws ResourceNotFoundException;

}
