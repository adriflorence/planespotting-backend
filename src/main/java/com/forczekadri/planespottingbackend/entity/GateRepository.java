package com.forczekadri.planespottingbackend.entity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface GateRepository extends CrudRepository<Gate, Long> {

    @Query("SELECT g FROM Gate g WHERE g.in_use = false")
    Collection<Gate> getAllFreeGates();

}
