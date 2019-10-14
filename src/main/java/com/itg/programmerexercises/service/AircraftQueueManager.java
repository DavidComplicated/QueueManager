package com.itg.programmerexercises.service;

import com.itg.programmerexercises.objects.aircraft.Aircraft;
import com.itg.programmerexercises.objects.system.SystemStatus;

import java.util.concurrent.ConcurrentLinkedQueue;

public interface AircraftQueueManager
{

	/**
	 * <p>
	 * Initialize and start the system
	 * </p>
	 */
	void systemBoot();

	/**
	 * <p>
	 * Enqueue aircraft used to insert a new AC into the system
	 * </p>
	 * 
	 * @param aircraft
	 * @return
	 */
	Aircraft enqueueAc(Aircraft aircraft);

	/**
	 * <p>
	 * <Dequeue aircraft used to remove an AC from the system/p>
	 *
	 * @return
	 */
	Aircraft dequeueAc();

	ConcurrentLinkedQueue getQueue();

	Aircraft getNextAircraftToDequeue();

	SystemStatus getSystemStatus();

}
