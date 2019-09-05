package com.forczekadri.planespottingbackend.controller;

import com.forczekadri.planespottingbackend.entity.Flight;
import com.forczekadri.planespottingbackend.entity.FlightRepository;
import com.forczekadri.planespottingbackend.service.FakeTime;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("flights") //  /flights
public class FlightController {

    private FlightRepository flightRepository;
    private FakeTime fakeTime;

    @GetMapping
    public Iterable<Flight> getAllFlights(){
        ZoneOffset offset = ZoneId.of("Europe/London").getRules().getOffset(fakeTime.instant());
        LocalDateTime currentTime = LocalDateTime.ofInstant(fakeTime.instant(), offset);
        return flightRepository.getAllCurrentFlights(currentTime);
    }

    public FlightController(FlightRepository flightRepository, FakeTime fakeTime) {
        this.flightRepository = flightRepository;
        this.fakeTime = fakeTime;
    }
}
