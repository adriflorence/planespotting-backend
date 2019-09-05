package com.forczekadri.planespottingbackend.service;

import com.forczekadri.planespottingbackend.entity.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Component
public class SchedulerService {

    private FlightRepository flightRepository;
    private AirportRepository airportRepository;
    private GateRepository gateRepository;
    private FakeTime fakeTime;

    public SchedulerService(FlightRepository flightRepository, AirportRepository airportRepository, GateRepository gateRepository, FakeTime fakeTime) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
        this.gateRepository = gateRepository;
        this.fakeTime = fakeTime;
    }

    @Scheduled(fixedRate = 20000)
    public void scheduleFlights() {
        ZoneOffset offset = ZoneId.of("Europe/London").getRules().getOffset(fakeTime.instant());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(fakeTime.instant(), offset);

        Airport airport = getRandomAirport();
        Gate gate = getRandomGate();
        Flight flight = new Flight(airport, localDateTime.plusHours(1), gate);
        flightRepository.save(flight);
    }

    @Scheduled(fixedRate = 6000)
    public void updateFlights(){
        ZoneOffset offset = ZoneId.of("Europe/London").getRules().getOffset(fakeTime.instant());
        LocalDateTime currentTime = LocalDateTime.ofInstant(fakeTime.instant(), offset);
        Collection<Flight> allCurrentFlights = flightRepository.getAllCurrentFlights(currentTime);
        for (Flight flight : allCurrentFlights) {
            long minutes = ChronoUnit.MINUTES.between(currentTime, flight.getTime());
            if(minutes < 40 && minutes > 30) flight.setStatus("Go to Gate!");
            if(minutes <= 30) flight.setStatus("Boarding");
            if(minutes == 0) {
                flight.setStatus("Departed");
                Gate gate = flight.getGate();
                gate.setIn_use(false);
                gateRepository.save(gate);
            }
            flightRepository.save(flight);
        }
    }

    public Airport getRandomAirport(){
        List<Airport> airports = (List<Airport>) airportRepository.findAll(); // repo returns iterable type
        Airport airport = airports.get(new Random().nextInt(airports.size()));
        return airport;
    }

    public Gate getRandomGate(){
        List<Gate> gates = (List<Gate>) gateRepository.getAllFreeGates();
        Gate gate = gates.get(new Random().nextInt(gates.size()));
        gate.setIn_use(true);
        gateRepository.save(gate);
        return gate;
    }

}
