package com.forczekadri.planespottingbackend.controller;

import com.forczekadri.planespottingbackend.entity.Airport;
import com.forczekadri.planespottingbackend.entity.AirportRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("airports")
public class AirportController {

    private AirportRepository airportRepository;

    @GetMapping
    public Iterable<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    public AirportController(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }
}
