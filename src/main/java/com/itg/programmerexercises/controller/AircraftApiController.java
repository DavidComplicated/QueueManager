package com.itg.programmerexercises.controller;

import com.itg.programmerexercises.exception.NoAircraftFoundException;
import com.itg.programmerexercises.objects.aircraft.Aircraft;
import com.itg.programmerexercises.objects.aircraft.AircraftDto;
import com.itg.programmerexercises.objects.aircraft.enums.AircraftSize;
import com.itg.programmerexercises.objects.aircraft.enums.AircraftType;
import com.itg.programmerexercises.objects.system.SystemStatus;
import com.itg.programmerexercises.service.AircraftQueueManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aqmRequestProcess")
@CrossOrigin
public class AircraftApiController {

    private final AircraftQueueManager aircraftQueueManager;

    @Autowired
    public AircraftApiController(AircraftQueueManager aircraftQueueManager) {
        this.aircraftQueueManager = aircraftQueueManager;
    }

    // Develop the code for a REST endpoint called aqmRequestProcess()
    // which accepts any of the three defined requests and follows the guidelines to implement an aircraft queue manager.
    @GetMapping
    public Aircraft aqmRequestProcess() {
        return Aircraft.builder().aircraftType(AircraftType.PASSENGER).aircraftSize(AircraftSize.SMALL).build();
    }

    @GetMapping(value = "/nexttodequeue")
    public Aircraft nextToDequeue() {
        Aircraft aircraft = this.aircraftQueueManager.getNextAircraftToDequeue();
        if (aircraft == null) {
            // We do not want to return a successful status if nothing has changed as post is not idempotent
            throw new NoAircraftFoundException("No aircraft is available to dequeue");
        }
        return aircraft;
    }

    // Created For Front End Purposes
    @GetMapping(value = "/status")
    public SystemStatus getSystemStatus() {
        return this.aircraftQueueManager.getSystemStatus();
    }

    @PostMapping(value = "/boot")
    public ResponseEntity startSystem(){
        this.aircraftQueueManager.systemBoot();
        return ResponseEntity.ok("System Started/Restarted");
    }

    @PostMapping(value = "/enqueue")
    public ResponseEntity addAircraft(@RequestBody AircraftDto aircraftDto) {
        Aircraft aircraft = aircraftDto.getAircraft();
        this.aircraftQueueManager.enqueueAc(aircraft);
        return new ResponseEntity<>(aircraft, HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/dequeue")
    public Aircraft removeAircraft() {
        Aircraft aircraft = this.aircraftQueueManager.dequeueAc();
        if (aircraft == null) {
            // We do not want to return a successful status if nothing has changed as post is not idempotent
            throw new NoAircraftFoundException("No aircraft is available to dequeue");
        }
        return aircraft;
    }

}
