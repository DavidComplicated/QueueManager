package com.itg.programmerexercises.service;

import com.itg.programmerexercises.objects.aircraft.Aircraft;
import com.itg.programmerexercises.objects.aircraft.enums.AircraftSize;
import com.itg.programmerexercises.objects.aircraft.enums.AircraftType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AircraftQueueManagerImplTest {

    @Autowired
    AircraftQueueManagerImpl queueManager; // Inject Concrete Class For Testing

    @Before
    public void bootSystem() {
        queueManager.systemBoot();
    }

    @Test
    public void addLargePassengerAircraft() {
        AircraftSize size = AircraftSize.LARGE;
        AircraftType type = AircraftType.PASSENGER;
        addAircraftToQueue(size, type);

        int qSize = queueManager.getQueue(type, size).get().size();
        Assert.assertEquals(1, qSize);
    }

    @Test
    public void addSmallPassengerAircraft() {
        AircraftSize size = AircraftSize.SMALL;
        AircraftType type = AircraftType.PASSENGER;
        addAircraftToQueue(size, type);

        int qSize = queueManager.getQueue(type, size).get().size();
        Assert.assertEquals(1, qSize);
    }

    @Test
    public void addLargeCargoAircraft() {
        AircraftSize size = AircraftSize.LARGE;
        AircraftType type = AircraftType.CARGO;
        addAircraftToQueue(size, type);

        int qSize = queueManager.getQueue(type, size).get().size();
        Assert.assertEquals(1, qSize);
    }

    @Test
    public void addSmallCargoAircraft() {
        AircraftSize size = AircraftSize.SMALL;
        AircraftType type = AircraftType.CARGO;
        addAircraftToQueue(size, type);

        int qSize = queueManager.getQueue(type, size).get().size();
        Assert.assertEquals(1, qSize);
    }

    @Test
    public void addAllAircraft() {
        addAircraftToQueue(AircraftSize.SMALL, AircraftType.CARGO);
        addAircraftToQueue(AircraftSize.LARGE, AircraftType.CARGO);
        addAircraftToQueue(AircraftSize.SMALL, AircraftType.PASSENGER);
        addAircraftToQueue(AircraftSize.LARGE, AircraftType.PASSENGER);
        int qSize = queueManager.getQueue(AircraftType.CARGO, AircraftSize.SMALL).get().size();
        Assert.assertEquals(1, qSize);
        qSize = queueManager.getQueue(AircraftType.CARGO, AircraftSize.LARGE).get().size();
        Assert.assertEquals(1, qSize);
        qSize = queueManager.getQueue(AircraftType.PASSENGER, AircraftSize.SMALL).get().size();
        Assert.assertEquals(1, qSize);
        qSize = queueManager.getQueue(AircraftType.PASSENGER, AircraftSize.LARGE).get().size();
        Assert.assertEquals(1, qSize);
    }

    @Test
    public void verifyDequeuePriority() {
        Aircraft aircraft1 = addAircraftToQueue(AircraftSize.SMALL, AircraftType.CARGO);
        Aircraft aircraft2 = addAircraftToQueue(AircraftSize.LARGE, AircraftType.CARGO);
        Aircraft aircraft3 = addAircraftToQueue(AircraftSize.SMALL, AircraftType.PASSENGER);
        Aircraft aircraft4 = addAircraftToQueue(AircraftSize.LARGE, AircraftType.PASSENGER);
        Aircraft aircraft5 = addAircraftToQueue(AircraftSize.SMALL, AircraftType.CARGO);
        Aircraft aircraft6 = addAircraftToQueue(AircraftSize.LARGE, AircraftType.CARGO);
        Aircraft aircraft7 = addAircraftToQueue(AircraftSize.SMALL, AircraftType.PASSENGER);
        Aircraft aircraft8 = addAircraftToQueue(AircraftSize.LARGE, AircraftType.PASSENGER);

        Assert.assertEquals(8, queueManager.getQueueSize());

        // Should Be two passenger larges and then the two passenger smalls
        Aircraft aircraft = queueManager.dequeueAc();
        Assert.assertEquals(AircraftType.PASSENGER, aircraft.getAircraftType());
        Assert.assertEquals(AircraftSize.LARGE, aircraft.getAircraftSize());
        Assert.assertEquals(aircraft4.getTimeAdded(), aircraft.getTimeAdded());
        aircraft = queueManager.dequeueAc();
        Assert.assertEquals(AircraftType.PASSENGER, aircraft.getAircraftType());
        Assert.assertEquals(AircraftSize.LARGE, aircraft.getAircraftSize());
        Assert.assertEquals(aircraft8.getTimeAdded(), aircraft.getTimeAdded());
        aircraft = queueManager.dequeueAc();
        Assert.assertEquals(AircraftType.PASSENGER, aircraft.getAircraftType());
        Assert.assertEquals(AircraftSize.SMALL, aircraft.getAircraftSize());
        Assert.assertEquals(aircraft3.getTimeAdded(), aircraft.getTimeAdded());
        aircraft = queueManager.dequeueAc();
        Assert.assertEquals(AircraftType.PASSENGER, aircraft.getAircraftType());
        Assert.assertEquals(AircraftSize.SMALL, aircraft.getAircraftSize());
        Assert.assertEquals(aircraft7.getTimeAdded(), aircraft.getTimeAdded());

        // Should Be two cargo larges and then the two cargo smalls
        aircraft = queueManager.dequeueAc();
        Assert.assertEquals(AircraftType.CARGO, aircraft.getAircraftType());
        Assert.assertEquals(AircraftSize.LARGE, aircraft.getAircraftSize());
        Assert.assertEquals(aircraft2.getTimeAdded(), aircraft.getTimeAdded());
        aircraft = queueManager.dequeueAc();
        Assert.assertEquals(AircraftType.CARGO, aircraft.getAircraftType());
        Assert.assertEquals(AircraftSize.LARGE, aircraft.getAircraftSize());
        Assert.assertEquals(aircraft6.getTimeAdded(), aircraft.getTimeAdded());
        aircraft = queueManager.dequeueAc();
        Assert.assertEquals(AircraftType.CARGO, aircraft.getAircraftType());
        Assert.assertEquals(AircraftSize.SMALL, aircraft.getAircraftSize());
        Assert.assertEquals(aircraft1.getTimeAdded(), aircraft.getTimeAdded());
        aircraft = queueManager.dequeueAc();
        Assert.assertEquals(AircraftType.CARGO, aircraft.getAircraftType());
        Assert.assertEquals(AircraftSize.SMALL, aircraft.getAircraftSize());
        Assert.assertEquals(aircraft5.getTimeAdded(), aircraft.getTimeAdded());
    }

    private Aircraft addAircraftToQueue(AircraftSize size, AircraftType type) {
        Aircraft aircraft = Aircraft.builder()
                .aircraftSize(size)
                .aircraftType(type)
                .build();
        queueManager.enqueueAc(aircraft);
        return aircraft;
    }
}
