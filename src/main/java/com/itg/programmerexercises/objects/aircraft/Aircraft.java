package com.itg.programmerexercises.objects.aircraft;

import com.itg.programmerexercises.objects.aircraft.enums.AircraftSize;
import com.itg.programmerexercises.objects.aircraft.enums.AircraftType;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * AC’s have at least (but are not limited to having) the following properties: <br>
 * - AC type: Passenger or Cargo <br>
 * - AC size: Small or Large
 * 
 * @author jpowell
 */
public class Aircraft
{

	private AircraftType aircraftType;

	private AircraftSize aircraftSize;

	private ZonedDateTime timeAdded; // To avoid timezone ambiguity with server

	private Aircraft(AircraftBuilder aircraftBuilder)
	{
		this.aircraftType = aircraftBuilder.aircraftType;
		this.aircraftSize = aircraftBuilder.aircraftSize;
		this.timeAdded = ZonedDateTime.now(ZoneId.of("Z"));
	}

	public AircraftType getAircraftType()
	{
		return aircraftType;
	}

	public void setAircraftType(AircraftType aircraftType)
	{
		this.aircraftType = aircraftType;
	}

	public AircraftSize getAircraftSize()
	{
		return aircraftSize;
	}

	public void setAircraftSize(AircraftSize aircraftSize)
	{
		this.aircraftSize = aircraftSize;
	}

	public ZonedDateTime getTimeAdded() {
		return timeAdded;
	}

	public void setTimeAdded(ZonedDateTime timeAdded) {
		this.timeAdded = timeAdded;
	}

	@Override
	public String toString() {
		return "Aircraft{" +
				"aircraftType=" + aircraftType +
				", aircraftSize=" + aircraftSize +
				", timeAdded=" + timeAdded +
				'}';
	}

	public static AircraftBuilder builder()
	{
		return new AircraftBuilder();
	}

	/**
	 * Builder to build {@link Aircraft}.
	 */
	public static final class AircraftBuilder
	{
		private AircraftType aircraftType;

		private AircraftSize aircraftSize;

		private AircraftBuilder()
		{
		}

		public AircraftBuilder aircraftType(AircraftType aircraftType)
		{
			this.aircraftType = aircraftType;
			return this;
		}

		public AircraftBuilder aircraftSize(AircraftSize aircraftSize)
		{
			this.aircraftSize = aircraftSize;
			return this;
		}

		public Aircraft build()
		{
			return new Aircraft(this);
		}
	}

}
