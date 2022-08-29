package com.parkit.parkingsystem;

import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import java.util.Scanner;

@ExtendWith(MockitoExtension.class)

public class InputReaderUtilTest {
	
	// PROBLEME: L'ENTREE UTILISATEUR 'AUTOMATIQUE' NE MARCHE PAS
	
	@Mock
	private static Scanner scan;
	
	
	//@Test
	public void readSelectionTest() {
		when(scan.nextLine()).thenReturn("1");
		
		 InputReaderUtil inputReaderUtil = new InputReaderUtil() ;
	      
		  //String input = "1";
	      //InputStream in = new ByteArrayInputStream(input.getBytes());
	     // System.setIn(in);
	      
	      assertEquals(1, inputReaderUtil.readSelection());
	}
	
	// PROBLEME: L'ENTREE UTILISATEUR 'AUTOMATIQUE' NE MARCHE PAS
	//@Test
	public void readVehicleRegistrationNumberTest() throws Exception {
		 InputReaderUtil inputReaderUtil = new InputReaderUtil() ;
	      
		  String input = "ABCDEF";
	      InputStream in = new ByteArrayInputStream(input.getBytes());
	      System.setIn(in);
	      
	      String output = inputReaderUtil.readVehicleRegistrationNumber();
	      assertEquals(output, "ABCDEF");
	}
	

} 
