package com.forczekadri.planespottingbackend.entity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface FlightRepository extends CrudRepository<Flight, Long> {

    @Query("SELECT f FROM Flight f WHERE f.time > ?1 ORDER BY f.id ASC")
    Collection<Flight> getAllCurrentFlights(LocalDateTime time);

}
