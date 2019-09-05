package com.forczekadri.planespottingbackend.entity;

import com.forczekadri.planespottingbackend.service.FakeTime;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Random;

@Entity
@Table(name="flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name="airline")
    @Size(max=100)
    private String airline;

    @Column(name="flightcode")
    @Size(max=6)
    private String flightCode;

    @Column(name="direction")
    @Size(max=8)
    private String direction;

    @ManyToOne
    @JoinColumn(name="airport", nullable=false)
    private Airport airport;

    @ManyToOne
    @JoinColumn(name="gate", nullable=true)
    private Gate gate;

    @Column(name="status")
    @Size(max=20)
    private String status;

    @Column
    private LocalDateTime time;

    public Flight() {

    }

    public Flight(Airport airport, LocalDateTime time, Gate gate) {
        this.airline = generateAirlineName(airport);
        this.flightCode = generateFlightCode(airport);
        this.direction = generateDirection();
        this.airport = airport;
        this.gate = gate;
        this.status = "On time";
        this.time = time;
    }

    private String generateAirlineName(Airport airport){
        String country = airport.getCountry();
        String[] airlineVariations = {
                country + " Airline",
                "Fly " + country,
                "Jetstar " + country,
                "Jet " + country,
                "Air " + country,
                "Wings of " + country,
                country + " Air",
                country + " Airways",
                "Trans " + country + " Airlines",
                "Royal " + country + " Airlines"
        };
        Random rand = new Random();
        int random = rand.nextInt(airlineVariations.length);
        return airlineVariations[random];
    }

    private String generateFlightCode(Airport airport){
        String country = airport.getIcao().substring(0,2);
        String code = airport.getCode();
        StringBuilder flightCode = new StringBuilder();
        flightCode.append(country);
        for (int i = 0; i < code.length()-1; i++) {
            flightCode.append(generateAscii(code.charAt(i)));
        }
        return flightCode.toString();
    }

    private String generateAscii(char c){
        int ascii = c;
        return Integer.toString(ascii);
    }

    private String generateDirection(){
        String[] direction = {"inbound", "outbound"};
        Random rand = new Random();
        return direction[rand.nextInt(2)];
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", airline='" + airline + '\'' +
                ", flightCode='" + flightCode + '\'' +
                ", direction='" + direction + '\'' +
                ", airport=" + airport +
                ", status='" + status + '\'' +
                ", time=" + time +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public Gate getGate() {
        return gate;
    }

    public void setGate(Gate gate) {
        this.gate = gate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
