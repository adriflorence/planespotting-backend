package com.forczekadri.planespottingbackend.service;

import com.forczekadri.planespottingbackend.entity.Airport;
import com.forczekadri.planespottingbackend.entity.AirportRepository;
import com.forczekadri.planespottingbackend.entity.Flight;
import com.forczekadri.planespottingbackend.entity.FlightRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class SchedulerService {

    private FlightRepository flightRepository;
    private AirportRepository airportRepository;

    @Scheduled(fixedRate = 10000)
    public void scheduleFlights() {
        List<Airport> airports = (List<Airport>) airportRepository.findAll();
        Airport airport = airports.get(new Random().nextInt(airports.size()));
        Flight flight = new Flight(airport);
        flightRepository.save(flight);
        System.out.println(flight);
    }

    public SchedulerService(FlightRepository flightRepository, AirportRepository airportRepository){
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
    }
}
