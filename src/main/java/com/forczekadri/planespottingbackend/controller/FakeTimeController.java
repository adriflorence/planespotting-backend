package com.forczekadri.planespottingbackend.controller;

import com.forczekadri.planespottingbackend.service.FakeTime;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("faketime")
public class FakeTimeController {

    private FakeTime faketime;

    public FakeTimeController(FakeTime faketime) {
        this.faketime = faketime;
    }

    @GetMapping
    public Instant getFakeTime() {
        return faketime.instant();
    }
}
