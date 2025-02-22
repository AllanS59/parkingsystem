package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

	public void calculateFare(Ticket ticket) {
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}

		long inHour = ticket.getInTime().getTime();
		long outHour = ticket.getOutTime().getTime();

		double duration = (outHour - inHour) / (double) 1000; // duration in seconds
		duration = duration / (60 * 60); // duration converted in hours

		double ratioPriceForRecurrentUser = 1;
		if (ticket.getDiscountStatus()) {
			ratioPriceForRecurrentUser = 1 - (Fare.DISCOUNT_RATE_FOR_RECURRENT_USERS) / 100;
		}

		if (duration <= 0.5)
			ticket.setPrice(0);
		else {
			switch (ticket.getParkingSpot().getParkingType()) {
			case CAR: {
				ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR * ratioPriceForRecurrentUser);
				break;
			}
			case BIKE: {
				ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR * ratioPriceForRecurrentUser);
				break;
			}
			default:
				throw new IllegalArgumentException("Unkown Parking Type");
			}
		}
	}
}