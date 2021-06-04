package br.com.supernova.backroom.service;

import br.com.supernova.backroom.builder.RoomDTOBuilder;
import br.com.supernova.backroom.exception.ResourceNotFoundException;
import br.com.supernova.backroom.exception.ResourceRoomAlreadyRegisteredException;
import br.com.supernova.backroom.mapper.RoomMapper;
import br.com.supernova.backroom.model.dto.RoomDTO;
import br.com.supernova.backroom.model.entity.Room;
import br.com.supernova.backroom.repository.MeetingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    private RoomMapper mapper = RoomMapper.INSTANCE;
    private static final Long INVALID_ROOM_ID = 2L;
    private static final String CHECKED_NAME = "BUSINESS ROOM";

    @Mock
    private MeetingRepository repository;

    @InjectMocks
    private RoomService service;

    @Test
    void whenRoomDTOProvidedThenReturnResponseEntityOK() throws ResourceRoomAlreadyRegisteredException {
        RoomDTO buildDTO = RoomDTOBuilder.builder().build().toRoomDTO();
        Room roomEntity = mapper.toModel(buildDTO);

        // WHEN
        when(repository.findByName(buildDTO.getName())).thenReturn(Optional.empty());
        when(repository.save(any(Room.class))).thenReturn(roomEntity);

        // THEN
        RoomDTO meetingRoom = service.createMeetingRoom(buildDTO);

        // HAMCREST
        assertThat(meetingRoom.getId(), is(equalTo(roomEntity.getId())));
        assertThat(meetingRoom.getName(), is(equalTo(roomEntity.getName())));
    }

    @Test
    void whenAlreadyRegisteredRoomDTOThenAnExceptionShouldBeThrow(){
        RoomDTO builderDTO = RoomDTOBuilder.builder().build().toRoomDTO();
        Room roomEntity = mapper.toModel(builderDTO);

        // WHEN
        when(repository.findByName(builderDTO.getName())).thenReturn(Optional.of(roomEntity));

        // THEN
        assertThrows(ResourceRoomAlreadyRegisteredException.class, () -> service.createMeetingRoom(builderDTO));
    }

    @Test
    void whenANameRoomInformedThenARoomDTOMustBeReturned() throws ResourceNotFoundException {
        RoomDTO builderDTO = RoomDTOBuilder.builder().build().toRoomDTO();
        Room roomEntity = mapper.toModel(builderDTO);

        // WHEN
        when(repository.findByName(builderDTO.getName())).thenReturn(Optional.of(roomEntity));

        // THEN
        RoomDTO meetingRoomByName = service.getMeetingRoomByName(builderDTO.getName());

        // HAMCREST
        assertThat(meetingRoomByName.getName(), is(equalTo(roomEntity.getName())));
    }

    @Test
    void whenRoomNameNotFoundThenAnExceptionShouldBeThrown() {

        // WHEN
        when(repository.findByName(CHECKED_NAME)).thenReturn(Optional.empty());

        // THEN
        assertThrows(ResourceNotFoundException.class, () -> service.getMeetingRoomByName(CHECKED_NAME));
    }

    @Test
    void whenAnIDValidIsInformedARoomDTOMustBeReturned() throws ResourceNotFoundException {
        RoomDTO builderDTO = RoomDTOBuilder.builder().build().toRoomDTO();
        Room roomEntity = mapper.toModel(builderDTO);

        // WHEN
        when(repository.findById(builderDTO.getId())).thenReturn(Optional.of(roomEntity));

        // THEN
        RoomDTO meetingRoomByID = service.getMeetingRoomByID(builderDTO.getId());

        assertThat(meetingRoomByID.getId(), is(equalTo(roomEntity.getId())));
    }

    @Test
    void whenInformedInvalidIDRoomThenAnExceptionShouldBeThrown() {

        // WHEN
        when(repository.findById(INVALID_ROOM_ID)).thenReturn(Optional.empty());

        // THEN
        assertThrows(ResourceNotFoundException.class, () -> service.getMeetingRoomByID(INVALID_ROOM_ID));
    }

    /*
     *
    @Test
    void whenAskedForTheListOfMeetingRoomsThenReturnedAllRoomsDTO(){
        RoomDTO builderDTO = RoomDTOBuilder.builder().build().toRoomDTO();
        Room roomEntity = mapper.toModel(builderDTO);

        List<Room> roomList = Arrays.asList(roomEntity);
        Page<Room> roomPage = new PageImpl(roomList);

        // WHEN
        when(repository.findAll(any(Pageable.class))).thenReturn(roomPage);

        // THEN
        List<RoomDTO> allMeetingRooms = service.getAllMeetingRooms(any(Pageable.class));
        assertThat(allMeetingRooms, is(not(empty())));
        assertThat(allMeetingRooms.get(0), is(equalTo(builderDTO)));
    }
    */
    @Test
    void whenInformedIDToUpdateRoomThenReturnModifiedRoomDTO() throws ResourceNotFoundException {
        RoomDTO builderDTO = RoomDTOBuilder.builder().build().toRoomDTO();

        Room updateEntity = mapper.toModel(builderDTO);
        updateEntity.setName(CHECKED_NAME);

        Room compareEntity = mapper.toModel(builderDTO);

        // WHEN
        when(repository.findById(builderDTO.getId())).thenReturn(Optional.of(compareEntity));
        when(repository.save(any(Room.class))).thenReturn(compareEntity);

        // THEN
        RoomDTO returnRoomDTO = service.updateMeetingRoom(compareEntity.getId(), builderDTO);

        // HAMCREST
        assertThat(returnRoomDTO.getId(), is(equalTo(compareEntity.getId())));
        assertThat(returnRoomDTO.getName(), is(equalTo(compareEntity.getName())));
    }

    @Test
    void whenInformedInvalidRoomIDToUpdateTheRoomThenANotFoundExceptionIsThrown() {

        // WHEN
        when(repository.findById(INVALID_ROOM_ID)).thenReturn(Optional.empty());

        // THEN
        assertThrows(ResourceNotFoundException.class, () -> service.updateMeetingRoom(INVALID_ROOM_ID, RoomDTO.builder().build()));
    }

    @Test
    void whenInformedValidIDToDeleteRoomThenAMapMustBeReturned() throws ResourceNotFoundException {
        RoomDTO buildDTO = RoomDTOBuilder.builder().build().toRoomDTO();
        Room roomEntity = mapper.toModel(buildDTO);

        // WHEN
        when(repository.findById(buildDTO.getId())).thenReturn(Optional.of(roomEntity));
        doNothing().when(repository).delete(roomEntity);

        // THEN
        service.deleteMeetingRoom(buildDTO.getId());
        verify(repository, times(1)).findById(buildDTO.getId());
        verify(repository, times(1)).delete(roomEntity);
    }

    @Test
    void whenInformedInvalidRoomIDToDeleteTheRoomThenANotFoundExceptionIsThrown() {

        // WHEN
        when(repository.findById(INVALID_ROOM_ID)).thenReturn(Optional.empty());

        // THEN
        assertThrows(ResourceNotFoundException.class, () -> service.deleteMeetingRoom(INVALID_ROOM_ID));
    }
}
