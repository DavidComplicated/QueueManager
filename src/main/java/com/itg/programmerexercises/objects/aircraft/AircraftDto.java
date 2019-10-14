package com.itg.programmerexercises.objects.aircraft;

import com.itg.programmerexercises.exception.CannotCreateAircraftException;
import com.itg.programmerexercises.objects.aircraft.enums.AircraftSize;
import com.itg.programmerexercises.objects.aircraft.enums.AircraftType;

public class AircraftDto {
    private String aircraftType;
    private String aircraftSize;

    public String getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }

    public String getAircraftSize() {
        return aircraftSize;
    }

    public void setAircraftSize(String aircraftSize) {
        this.aircraftSize = aircraftSize;
    }

    public Aircraft getAircraft() {
        Aircraft aircraft = null;
        try {
            aircraft = Aircraft.builder()
                    .aircraftSize(Enum.valueOf(AircraftSize.class, this.aircraftSize))
                    .aircraftType(Enum.valueOf(AircraftType.class, this.aircraftType))
                    .build();
        } catch (IllegalArgumentException e) {
            throw new CannotCreateAircraftException(
                    "Cannot Create Aircraft - Invalid aircraftType or aircraftSize. Please check and try again"
            );
        }
        return aircraft;
    }

    @Override
    public String toString() {
        return "AircraftDto{" +
                "aircraftType='" + aircraftType + '\'' +
                ", aircraftSize='" + aircraftSize + '\'' +
                '}';
    }
}
