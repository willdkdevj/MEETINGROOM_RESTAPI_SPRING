package br.com.supernova.backroom.service;

import br.com.supernova.backroom.exception.ResourceNotFoundException;
import br.com.supernova.backroom.exception.ResourceRoomAlreadyRegisteredException;
import br.com.supernova.backroom.mapper.RoomMapper;
import br.com.supernova.backroom.model.dto.RoomDTO;
import br.com.supernova.backroom.model.entity.Room;
import br.com.supernova.backroom.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final MeetingRepository repository;
    private final RoomMapper mapper = RoomMapper.INSTANCE;

    public List<RoomDTO> getAllMeetingRooms(){
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public RoomDTO getMeetingRoomByID(Long id) throws ResourceNotFoundException {
        Room foundRoom = checkIfThereIsRoomByID(id);

        String date = returnDateFormatter(foundRoom.getDate());
        RoomDTO roomDTO = mapper.toDTO(foundRoom);
        roomDTO.setDate(date);


        return roomDTO;
    }

    public RoomDTO getMeetingRoomByName(String name) throws ResourceNotFoundException {
        Room foundRoom = repository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException(name)
        );

        String date = returnDateFormatter(foundRoom.getDate());
        RoomDTO roomDTO = mapper.toDTO(foundRoom);
        roomDTO.setDate(date);

        return roomDTO;
    }

    @Transactional
    public RoomDTO createMeetingRoom(RoomDTO roomDTO) throws ResourceRoomAlreadyRegisteredException {
        checkIfThereIsRoomByName(roomDTO.getName());

        Room model = mapper.toModel(roomDTO);
        Room savedRoom = repository.save(model);

        return mapper.toDTO(savedRoom);
    }

    @Transactional
    public RoomDTO updateMeetingRoom(Long id, RoomDTO roomDTO) throws ResourceNotFoundException {
        Room returnStatusRoom = checkIfThereIsRoomByID(id);

        Room model = mapper.toModel(roomDTO);
        model.setId(returnStatusRoom.getId());

        Room updatedRoom = repository.save(model);

        return mapper.toDTO(updatedRoom);
    }

    @Transactional
    public void deleteMeetingRoom(Long id) throws ResourceNotFoundException {
        Room foundRoom = checkIfThereIsRoomByID(id);
        repository.delete(foundRoom);
    }

    private Room checkIfThereIsRoomByID(Long id) throws ResourceNotFoundException {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
    }

    private void checkIfThereIsRoomByName(String name) throws ResourceRoomAlreadyRegisteredException {
        Optional<Room> returnStatusRoom = repository.findByName(name);
        if(returnStatusRoom.isPresent())
            throw new ResourceRoomAlreadyRegisteredException(name);
    }

    private String returnDateFormatter(LocalDate date){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formatDate = date.format(dateFormatter);
        return formatDate;
    }

}
