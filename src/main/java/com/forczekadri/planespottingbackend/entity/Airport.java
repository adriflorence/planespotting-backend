package com.forczekadri.planespottingbackend.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name="airports")
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Size(max=255)
    @Column(name="name")
    private String name;

    @Size(max=255)
    @Column(name="city")
    private String city;

    @Size(max=255)
    @Column(name="country")
    private String country;

    @Size(max=3)
    @Column(name="code")
    private String code;

    @Size(max=4)
    @Column(name="icao")
    private String icao;


    @OneToMany(cascade = CascadeType.ALL, mappedBy="airport", fetch = FetchType.LAZY)
    private List<Flight> flights;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }
}
