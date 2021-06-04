package br.com.supernova.backroom.repository;

import br.com.supernova.backroom.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeetingRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByName(String name);
}
