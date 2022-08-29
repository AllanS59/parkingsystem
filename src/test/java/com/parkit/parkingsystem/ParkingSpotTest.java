package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParkingSpotTest {

	@Test
	public void checkEqualsMethodWhenEqual() {
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		ParkingSpot parkingSpot2 = new ParkingSpot(1, ParkingType.CAR, false);

		boolean checkEquals = parkingSpot.equals(parkingSpot2);

		assertEquals(true, checkEquals);
	}

	@Test
	public void checkEqualsMethodWhenNotEqual() {
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		ParkingSpot parkingSpot2 = new ParkingSpot(2, ParkingType.CAR, false);

		boolean checkEquals = parkingSpot.equals(parkingSpot2);

		assertEquals(false, checkEquals);
	}
}
