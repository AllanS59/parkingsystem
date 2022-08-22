package com.parkit.parkingsystem;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class TicketDAOTest {

	@InjectMocks
	private TicketDAO ticketDAO;
	@Mock
	private static PreparedStatement ps;
	@Mock
	private static Connection con;
	@Mock
	DataBaseConfig dataBaseConfig;
	@Mock
	private ResultSet result;
	private Ticket ticket;


	@BeforeEach
	public void init() throws SQLException, ClassNotFoundException {
		MockitoAnnotations.initMocks(this);
		Mockito.when(con.createStatement()).thenReturn(ps);
		Mockito.when(dataBaseConfig.getConnection()).thenReturn(con);
		

		ticket = new Ticket();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		ticket.setParkingSpot(parkingSpot);
		ticket.setVehicleRegNumber("ABCDEF");
		ticket.setPrice(0);
		Date inTime = new Date();
		ticket.setInTime(inTime);
		ticket.setOutTime(null);
		}
	
	@Test
	public void saveTicketTest() throws SQLException {
		Mockito.when(con.prepareStatement(DBConstants.SAVE_TICKET)).thenReturn(ps);
		
		ticketDAO.saveTicket(ticket);
		Mockito.verify(ps, times(1)).execute();	
	}

	@Test
	public void getTicketTest() throws SQLException {		
		Mockito.when(con.prepareStatement(DBConstants.GET_TICKET)).thenReturn(ps);
		Mockito.when(ps.executeQuery()).thenReturn(result);
		Mockito.when(result.next()).thenReturn(true);
		
		Mockito.when(result.getInt(1)).thenReturn(1);
		Mockito.when(result.getInt(2)).thenReturn(0);
		Mockito.when(result.getDouble(3)).thenReturn(0.0);
		Date inTime = new Date();
		Timestamp inTimeStamp = new Timestamp(inTime.getTime());
		Mockito.when(result.getTimestamp(4)).thenReturn(inTimeStamp);
		Mockito.when(result.getTimestamp(5)).thenReturn(inTimeStamp);
		Mockito.when(result.getBoolean(6)).thenReturn(false);
		Mockito.when(result.getString(7)).thenReturn( "CAR");
		
		Ticket ticket = ticketDAO.getTicket("ABCDEF");
		assertEquals(1,ticket.getParkingSpot().getId());
		assertNotNull(ticket.getInTime());
		Mockito.verify(ps).executeQuery();
	}
	
	
	@Test
	public void checkRecurrentUserTest() throws SQLException {		
		Mockito.when(con.prepareStatement(DBConstants.CHECK_RECURRENT_USER)).thenReturn(ps);
		Mockito.when(ps.executeQuery()).thenReturn(result);
		Mockito.when(result.next()).thenReturn(true);
		
		Mockito.when(result.getInt(1)).thenReturn(2);   
		
		boolean IsRecurrent = false;
		IsRecurrent = ticketDAO.checkRecurrentUser("ABCDEF");
		assertEquals(true,IsRecurrent);
		Mockito.verify(ps).executeQuery();
	}

	
	@Test
	public void updateTicketTest() throws SQLException {		
		Mockito.when(con.prepareStatement(DBConstants.UPDATE_TICKET)).thenReturn(ps);
		
		Date inTime = new Date();
		ticket.setOutTime(inTime);
		
		ticketDAO.updateTicket(ticket); 
		
		Mockito.verify(ps, times(1)).execute();	
	}
}
