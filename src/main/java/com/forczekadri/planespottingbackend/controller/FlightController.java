package com.forczekadri.planespottingbackend.controller;

import com.forczekadri.planespottingbackend.entity.Flight;
import com.forczekadri.planespottingbackend.entity.FlightRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("flights") //  /flights
public class FlightController {

    private FlightRepository flightRepository;

    @GetMapping
    public Iterable<Flight> getAllFlights(){
        return flightRepository.findAll();
    }

    public FlightController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }
}
