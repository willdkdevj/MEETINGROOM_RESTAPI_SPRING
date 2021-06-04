package br.com.supernova.backroom.util;

import br.com.supernova.backroom.model.dto.RoomDTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class UtilJsonToString {

    public static String jsonAsString(RoomDTO book) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.registerModule(new JavaTimeModule());

            return objectMapper.writeValueAsString(book);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
