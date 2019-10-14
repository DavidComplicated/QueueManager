package com.itg.programmerexercises.service;

import com.itg.programmerexercises.exception.CannotCreateAircraftException;
import com.itg.programmerexercises.objects.aircraft.Aircraft;
import com.itg.programmerexercises.objects.aircraft.enums.AircraftSize;
import com.itg.programmerexercises.objects.aircraft.enums.AircraftType;
import com.itg.programmerexercises.objects.system.SystemStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class AircraftQueueManagerImpl implements AircraftQueueManager {

    private final ConcurrentLinkedQueue<Aircraft> queuePassengerLarge = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Aircraft> queuePassengerSmall = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Aircraft> queueCargoLarge = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Aircraft> queueCargoSmall = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue[] queueArray;

    public AircraftQueueManagerImpl() {
        this.queueArray = new ConcurrentLinkedQueue[] {queuePassengerLarge, queuePassengerSmall, queueCargoLarge, queueCargoSmall};
    }

    @Override
    public void systemBoot() {
        queuePassengerLarge.clear();
        queuePassengerSmall.clear();
        queueCargoLarge.clear();
        queueCargoSmall.clear();
        System.out.println("System Booted");
    }

    @Override
    // This synchronized keyword is not really required as the list we are writing to are thread safe.
    // However, at the cost of a little performance we can guarantee exact order for fifo of list
    public synchronized Aircraft enqueueAc(Aircraft aircraft) {
        System.out.println("added: " + aircraft);
        Optional<ConcurrentLinkedQueue<Aircraft>> queueOptional = getQueue(aircraft.getAircraftType(), aircraft.getAircraftSize());
        queueOptional.ifPresent(queue -> queue.add(aircraft));
        return aircraft;
    }

    @Override
    public Aircraft dequeueAc() {
        return getNextAvailableAircraft(true);
    }

    @Override
    public Aircraft getNextAircraftToDequeue() {
        return getNextAvailableAircraft(false);
    }

    @Override
    public ConcurrentLinkedQueue getQueue() {
        return queuePassengerSmall;
    }

    @Override
    public SystemStatus getSystemStatus() {
        SystemStatus systemStatus = new SystemStatus();
        systemStatus.setTotalQueueSize(getQueueSize());
        systemStatus.setCargoLargeSize(getQueue(AircraftType.CARGO, AircraftSize.LARGE).get().size());
        systemStatus.setPassengerLargeSize(getQueue(AircraftType.PASSENGER, AircraftSize.LARGE).get().size());
        systemStatus.setCargoSmallSize(getQueue(AircraftType.CARGO, AircraftSize.SMALL).get().size());
        systemStatus.setPassengerSmallSize(getQueue(AircraftType.PASSENGER, AircraftSize.SMALL).get().size());
        systemStatus.setNextToDequeue(getNextAircraftToDequeue());
        return systemStatus;
    }

    public int getQueueSize() {
        int size = queuePassengerLarge.size();
        size += queuePassengerSmall.size();
        size += queueCargoLarge.size();
        size += queueCargoSmall.size();
        return size;
    }

    public Optional<ConcurrentLinkedQueue<Aircraft>> getQueue(AircraftType aircraftType, AircraftSize aircraftSize) throws CannotCreateAircraftException {
        ConcurrentLinkedQueue<Aircraft> queue = null;
        switch (aircraftType) {
            case PASSENGER:
                switch (aircraftSize) {
                    case LARGE:
                        queue = queuePassengerLarge;
                        break;
                    case SMALL:
                        queue = queuePassengerSmall;
                        break;
                }
                break;
            case CARGO:
                switch (aircraftSize) {
                    case LARGE:
                        queue = queueCargoLarge;
                        break;
                    case SMALL:
                        queue = queueCargoSmall;
                        break;
                }
                break;
        }

        return  Optional.ofNullable(queue);
    }

    // This method is locked to prevent an aircraft from being polled and another thread polling the next in line but
    // returning first. May not be worth the performance impact if in very high transaction environment
    private synchronized Aircraft getNextAvailableAircraft(boolean dequeue) {
        Aircraft ac = null;
        if (getQueueSize() == 0) {
            return  ac;
        }
        for (ConcurrentLinkedQueue<Aircraft>  queue: queueArray) {
            ac = (dequeue) ? queue.poll() : queue.peek();
            if (ac != null) {
                System.out.println((dequeue) ? "Dequeued: " : "Next to dequeue: " + ac);
                break;
            }
        }
        return ac;
    }


}
