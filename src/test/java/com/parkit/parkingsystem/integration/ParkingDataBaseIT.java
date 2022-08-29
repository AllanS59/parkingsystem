package com.parkit.parkingsystem.integration;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	private static ParkingSpotDAO parkingSpotDAO;
	private static TicketDAO ticketDAO;
	private static DataBasePrepareService dataBasePrepareService;

	@Mock
	private static InputReaderUtil inputReaderUtil;

	@BeforeAll
	private static void setUp() throws Exception {
		parkingSpotDAO = new ParkingSpotDAO();
		parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
		ticketDAO = new TicketDAO();
		ticketDAO.dataBaseConfig = dataBaseTestConfig;
		dataBasePrepareService = new DataBasePrepareService();
	}

	@BeforeEach
	private void setUpPerTest() throws Exception {
		when(inputReaderUtil.readSelection()).thenReturn(1);
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		dataBasePrepareService.clearDataBaseEntries();
	}

	@AfterAll
	private static void tearDown() {

	}

	@Test
	public void testParkingACar() {
		// GIVEN
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		parkingService.processIncomingVehicle();

		// WHEN
		Ticket ticket = ticketDAO.getTicket("ABCDEF");
		ParkingSpot parkingSpot = ticket.getParkingSpot();
		int parkingNumber = 0;
		int parkingIsAvailable = 1;
		String parkingType = "UNDEFINED";

		Connection con = null;
		try {
			con = dataBaseTestConfig.getConnection();
			PreparedStatement ps = con.prepareStatement(
					"select p.PARKING_NUMBER, p.AVAILABLE, p.TYPE from parking p where p.PARKING_NUMBER=?");
			ps.setInt(1, parkingSpot.getId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				parkingNumber = rs.getInt(1);
				parkingIsAvailable = rs.getInt(2);
				parkingType = rs.getString(3);

			}
			dataBaseTestConfig.closeResultSet(rs);
			dataBaseTestConfig.closePreparedStatement(ps);
		} catch (Exception ex) {

		} finally {
			dataBaseTestConfig.closeConnection(con);
		}

		// THEN
		assertEquals(1, parkingNumber); // Check that that the parking spot selected is the first one
		assertEquals(0, parkingIsAvailable); // Check that that the parking spot is available in DB
		assertEquals("CAR", parkingType); // Check that that the parking spot is available in DB

	}

	@Test
	public void testParkingLotExit() {
		// GIVEN
		testParkingACar();
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		parkingService.processExitingVehicle();

		// WHEN
		Ticket ticket = ticketDAO.getTicket("ABCDEF");
		ParkingSpot parkingSpot = ticket.getParkingSpot();
		int parkingNumber = 0;
		int parkingIsAvailable = 0;
		String parkingType = "UNDEFINED";

		Connection con = null;
		try {
			con = dataBaseTestConfig.getConnection();
			PreparedStatement ps = con.prepareStatement(
					"select p.PARKING_NUMBER, p.AVAILABLE, p.TYPE from parking p where p.PARKING_NUMBER=?");
			ps.setInt(1, parkingSpot.getId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				parkingNumber = rs.getInt(1);
				parkingIsAvailable = rs.getInt(2);
				parkingType = rs.getString(3);

			}
			dataBaseTestConfig.closeResultSet(rs);
			dataBaseTestConfig.closePreparedStatement(ps);
		} catch (Exception ex) {

		} finally {
			dataBaseTestConfig.closeConnection(con);
		}

		// THEN
		assertEquals(1, parkingNumber); // Check that that the parking spot selected is the first one
		assertEquals(1, parkingIsAvailable); // Check that that the parking spot is available in DB
		assertEquals("CAR", parkingType); // Check that that the parking spot is for correct vehicule
		assertNotNull(ticket.getOutTime()); // check that a getOutTime were saved in DB

	}

}
