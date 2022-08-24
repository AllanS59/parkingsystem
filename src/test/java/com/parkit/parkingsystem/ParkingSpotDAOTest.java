package com.parkit.parkingsystem;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.model.ParkingSpot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ParkingSpotDAOTest {

	@InjectMocks
	private ParkingSpotDAO parkingSpotDAO;
	@Mock
	private static PreparedStatement ps;
	@Mock
	private static Connection con;
	@Mock
	DataBaseConfig dataBaseConfig;
	@Mock
	private ResultSet result;
	private ParkingSpot parkingSpot;

	@BeforeEach
	public void init() throws SQLException, ClassNotFoundException {
		MockitoAnnotations.initMocks(this);
		Mockito.when(con.createStatement()).thenReturn(ps);
		Mockito.when(dataBaseConfig.getConnection()).thenReturn(con);
		parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
	}

	@Test
	public void getNextAvailableSlotTest() throws SQLException {
		Mockito.when(con.prepareStatement(DBConstants.GET_NEXT_PARKING_SPOT)).thenReturn(ps);
		Mockito.when(ps.executeQuery()).thenReturn(result);
		Mockito.when(result.next()).thenReturn(true);

		Mockito.when(result.getInt(1)).thenReturn(2);

		int nextAvailableSlot = parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR);
		assertEquals(2, nextAvailableSlot);
		Mockito.verify(ps, times(1)).executeQuery();
	}

	@Test
	public void updateParkingTest() throws SQLException {
		Mockito.when(con.prepareStatement(DBConstants.UPDATE_PARKING_SPOT)).thenReturn(ps);

		parkingSpotDAO.updateParking(parkingSpot);

		Mockito.verify(ps, times(1)).executeUpdate();
	}

}
