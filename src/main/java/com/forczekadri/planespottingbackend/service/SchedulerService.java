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

    @Scheduled(fixedRate = 3000) // 20000
    public void scheduleFlights() {
        ZoneOffset offset = ZoneId.of("Europe/London").getRules().getOffset(fakeTime.instant());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(fakeTime.instant(), offset);
        // round up to the nearest multiple of five minutes
        localDateTime = localDateTime.plusMinutes((65-localDateTime.getMinute())%5);

        Airport airport = getRandomAirport();
        Gate gate = getRandomGate();
        Flight flight = new Flight(airport, localDateTime.plusHours(1), gate);
        flightRepository.save(flight);
    }

    @Scheduled(fixedRate = 1000) // 6000
    public void updateFlights(){
        ZoneOffset offset = ZoneId.of("Europe/London").getRules().getOffset(fakeTime.instant());
        LocalDateTime currentTime = LocalDateTime.ofInstant(fakeTime.instant(), offset);

        // currentTime - 10
        // allows to see and manipulate flight info up to 10 minutes after arrival/departure
        LocalDateTime referenceTime = currentTime.minusMinutes(10);
        Collection<Flight> allCurrentFlights = flightRepository.getAllCurrentFlights(referenceTime);
        for (Flight flight : allCurrentFlights) {
            // Departures
            if(flight.getDirection().equals("outbound")){
                long minutes = ChronoUnit.MINUTES.between(currentTime, flight.getTime());
                if(flight.getTime().isAfter(currentTime)){
                    if(minutes < 40 && minutes > 30) flight.setStatus("Go to Gate");
                    if(minutes <= 30) flight.setStatus("Boarding");
                    if(minutes <= 15) flight.setStatus("Final Call");
                    if(minutes <= 10) flight.setStatus("Gate Closed");
                } else {
                    flight.setStatus("Departed");
                    Gate gate = flight.getGate();
                    gate.setIn_use(false);
                    gateRepository.save(gate);
                }
                // synchronise flight generation/exiting speed so there are always free gates
                // cleanse data to have country code
            } else if (flight.getDirection().equals("inbound")) {
                long minutes = ChronoUnit.MINUTES.between(flight.getTime(), currentTime);
                if(currentTime.isAfter(flight.getTime())){
                    if(minutes > 0) {
                        flight.setStatus("Landed");
                        Gate gate = flight.getGate();
                        gate.setIn_use(false);
                        gateRepository.save(gate);
                    }
                    if(minutes > 5) flight.setStatus("Baggage in Hall");
                }
            }
            flightRepository.save(flight);
        }
    }

    private Airport getRandomAirport(){
        List<Airport> airports = (List<Airport>) airportRepository.findAll(); // repo returns iterable type
        return airports.get(new Random().nextInt(airports.size()));
    }

    private Gate getRandomGate(){
        List<Gate> gates = (List<Gate>) gateRepository.getAllFreeGates();
        System.out.println(gates);
        Gate gate = gates.get(new Random().nextInt(gates.size()));
        System.out.println(gate);
        gate.setIn_use(true);
        gateRepository.save(gate);
        return gate;
    }

}
