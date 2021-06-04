package br.com.supernova.backroom.controller;

import br.com.supernova.backroom.builder.RoomDTOBuilder;
import br.com.supernova.backroom.exception.ResourceNotFoundException;
import br.com.supernova.backroom.model.dto.RoomDTO;
import br.com.supernova.backroom.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static br.com.supernova.backroom.util.UtilJsonToString.jsonAsString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RoomControllerTest {

    private static final String URL_PATH = "/api/v1";
    private static final Long INVALID_ID = 2L;
    private static final String PRE_PATH_FIND_ALL = "/rooms";
    private static final String PRE_PATH_FIND_BY_ID = "/room-id";
    private static final String PRE_PATH_FIND_BY_NAME = "/room-name";
    private static final String PRE_PATH_CREATE = "/new-room";
    private static final String PRE_PATH_UPDATE = "/update-room";
    private static final String PRE_PATH_DELETE = "/delete-room";

    private MockMvc mockMvc;

    @Mock
    private RoomService service;

    @InjectMocks
    private RoomController controller;

    @BeforeEach
    void SetUp() {
        controller = new RoomController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenARoomDTOIsCreated() throws Exception {
        RoomDTO builderDTO = RoomDTOBuilder.builder().build().toRoomDTO();

        when(service.createMeetingRoom(builderDTO)).thenReturn(builderDTO);

        mockMvc.perform(post(URL_PATH + PRE_PATH_CREATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonAsString(builderDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(builderDTO.getName())))
                .andExpect(jsonPath("$.date", is(builderDTO.getDate())))
                .andExpect(jsonPath("$.startHour", is(builderDTO.getStartHour())))
                .andExpect(jsonPath("$.endHour", is(builderDTO.getEndHour())));
    }

    @Test
    void whenPOSTIsCalledThenAndExceptionThrown() throws Exception {
        RoomDTO builderDTO = RoomDTOBuilder.builder().build().toRoomDTO();
        builderDTO.setName(null);

        mockMvc.perform(post(URL_PATH + PRE_PATH_CREATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonAsString(builderDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETIsCalledByNameRoomThenIsReturnedOK() throws Exception {
        RoomDTO builderDTO = RoomDTOBuilder.builder().build().toRoomDTO();

        when(service.getMeetingRoomByName(builderDTO.getName())).thenReturn(builderDTO);

        mockMvc.perform(get(URL_PATH + PRE_PATH_FIND_BY_NAME + "/" + builderDTO.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(builderDTO.getName())))
                .andExpect(jsonPath("$.date", is(builderDTO.getDate())))
                .andExpect(jsonPath("$.startHour", is(builderDTO.getStartHour())))
                .andExpect(jsonPath("$.endHour", is(builderDTO.getEndHour())));
    }

    @Test
    void whenGETIsCalledByInvalidNameRoomThenAnExceptionThrown() throws Exception {
        RoomDTO builderDTO = RoomDTOBuilder.builder().build().toRoomDTO();

        when(service.getMeetingRoomByName(builderDTO.getName())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(URL_PATH + PRE_PATH_FIND_BY_NAME + "/" + builderDTO.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETIsCalledByRoomIDThenIsReturnedOK() throws Exception {
        RoomDTO builderDTO = RoomDTOBuilder.builder().build().toRoomDTO();

        when(service.getMeetingRoomByID(builderDTO.getId())).thenReturn(builderDTO);

        mockMvc.perform(get(URL_PATH + PRE_PATH_FIND_BY_ID + "/" + builderDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(builderDTO.getName())))
                .andExpect(jsonPath("$.date", is(builderDTO.getDate())))
                .andExpect(jsonPath("$.startHour", is(builderDTO.getStartHour())))
                .andExpect(jsonPath("$.endHour", is(builderDTO.getEndHour())));
    }

    @Test
    void whenGETIsCalledByInvalidRoomIDThenAnExceptionThrown() throws Exception {
        RoomDTO builderDTO = RoomDTOBuilder.builder().build().toRoomDTO();

        when(service.getMeetingRoomByID(builderDTO.getId())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(URL_PATH + PRE_PATH_FIND_BY_ID + "/" + builderDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenPUTIsCalledThenAndExceptionThrown() throws Exception {
        RoomDTO builderDTO = RoomDTOBuilder.builder().build().toRoomDTO();

        when(service.updateMeetingRoom(builderDTO.getId(), builderDTO)).thenReturn(builderDTO);

        mockMvc.perform(put(URL_PATH + PRE_PATH_UPDATE + "/" + builderDTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonAsString(builderDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(builderDTO.getName())))
                .andExpect(jsonPath("$.date", is(builderDTO.getDate())))
                .andExpect(jsonPath("$.startHour", is(builderDTO.getStartHour())))
                .andExpect(jsonPath("$.endHour", is(builderDTO.getEndHour())));
    }

    @Test
    void whenDELETEIsCalledThenAndMapStatusReturned() throws Exception {
        mockMvc.perform(delete(URL_PATH + PRE_PATH_DELETE + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
