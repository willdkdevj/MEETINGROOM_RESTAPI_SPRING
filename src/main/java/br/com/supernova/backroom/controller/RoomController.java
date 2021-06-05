package br.com.supernova.backroom.controller;

import br.com.supernova.backroom.exception.ResourceNotFoundException;
import br.com.supernova.backroom.exception.ResourceRoomAlreadyRegisteredException;
import br.com.supernova.backroom.model.dto.RoomDTO;
import br.com.supernova.backroom.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RoomController implements RoomControllerImp{

    private final RoomService service;

    @GetMapping("/rooms")
    public ResponseEntity<List<RoomDTO>> getAllRooms(){
        return ResponseEntity.ok(service.getAllMeetingRooms());
    }

    @GetMapping("/room-id/{id}")
    public ResponseEntity<RoomDTO> findByID(@PathVariable Long id) throws ResourceNotFoundException {
        RoomDTO meetingRoomByID = service.getMeetingRoomByID(id);
        return ResponseEntity.ok(meetingRoomByID);
    }

    @GetMapping("/room-name/{name}")
    public ResponseEntity<RoomDTO> findByName(@PathVariable String name) throws ResourceNotFoundException {
        RoomDTO meetingRoomByName = service.getMeetingRoomByName(name);
        return ResponseEntity.ok(meetingRoomByName);
    }

    @PostMapping("/new-room")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RoomDTO> registerRoom(@Valid @RequestBody RoomDTO roomDTO) throws ResourceRoomAlreadyRegisteredException {
        RoomDTO createdMeetingRoom = service.createMeetingRoom(roomDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMeetingRoom);
    }

    @PutMapping("/update-room/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long id, @Valid @RequestBody RoomDTO roomDTO) throws ResourceNotFoundException, ResourceRoomAlreadyRegisteredException {
        RoomDTO updatedMeetingRoom = service.updateMeetingRoom(id, roomDTO);
        return ResponseEntity.ok(updatedMeetingRoom);
    }

    @DeleteMapping("/delete-room/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Map<String, Boolean>> deleteById(@PathVariable Long id) throws ResourceNotFoundException {
        service.deleteMeetingRoom(id);
        Map<String, Boolean> mapResponse = new HashMap<>();
        mapResponse.put("Deleted Register", true);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(mapResponse);
    }

}
