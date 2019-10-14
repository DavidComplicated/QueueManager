package com.itg.programmerexercises.objects.system;

import com.itg.programmerexercises.objects.aircraft.Aircraft;

public class SystemStatus {
    private int totalQueueSize;
    private int cargoSmallSize;
    private int cargoLargeSize;
    private int passengerSmallSize;
    private int passengerLargeSize;
    private Aircraft nextToDequeue;

    public int getTotalQueueSize() {
        return totalQueueSize;
    }

    public void setTotalQueueSize(int totalQueueSize) {
        this.totalQueueSize = totalQueueSize;
    }

    public int getCargoSmallSize() {
        return cargoSmallSize;
    }

    public void setCargoSmallSize(int cargoSmallSize) {
        this.cargoSmallSize = cargoSmallSize;
    }

    public int getCargoLargeSize() {
        return cargoLargeSize;
    }

    public void setCargoLargeSize(int cargoLargeSize) {
        this.cargoLargeSize = cargoLargeSize;
    }

    public int getPassengerSmallSize() {
        return passengerSmallSize;
    }

    public void setPassengerSmallSize(int passengerSmallSize) {
        this.passengerSmallSize = passengerSmallSize;
    }

    public int getPassengerLargeSize() {
        return passengerLargeSize;
    }

    public void setPassengerLargeSize(int passengerLargeSize) {
        this.passengerLargeSize = passengerLargeSize;
    }

    public Aircraft getNextToDequeue() {
        return nextToDequeue;
    }

    public void setNextToDequeue(Aircraft nextToDequeue) {
        this.nextToDequeue = nextToDequeue;
    }
}
