package com.parkit.parkingsystem;

import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;



public class InputReaderUtilTest {
	
	// PROBLEME: L'ENTREE UTILISATEUR 'AUTOMATIQUE' NE MARCHE PAS
	@Test
	public void readSelectionTest() {
		 InputReaderUtil inputReaderUtil = new InputReaderUtil() ;
	      
		  String input = "1";
	      InputStream in = new ByteArrayInputStream(input.getBytes());
	      System.setIn(in);
	      
	      assertEquals(1, inputReaderUtil.readSelection());
	}
	
	// PROBLEME: L'ENTREE UTILISATEUR 'AUTOMATIQUE' NE MARCHE PAS
	@Test
	public void readVehicleRegistrationNumberTest() throws Exception {
		 InputReaderUtil inputReaderUtil = new InputReaderUtil() ;
	      
		  String input = "ABCDEF";
	      InputStream in = new ByteArrayInputStream(input.getBytes());
	      System.setIn(in);
	      
	      String output = inputReaderUtil.readVehicleRegistrationNumber();
	      assertEquals(output, "ABCDEF");
	}
	

} 
