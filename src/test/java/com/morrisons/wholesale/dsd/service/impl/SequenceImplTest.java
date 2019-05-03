package com.morrisons.wholesale.dsd.service.impl;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SequenceImplTest {
	
	@InjectMocks
	SequenceImpl sequenceImpl;

	@Mock
	PreparedStatement prdStatement;

	@Mock
	Connection conn;
	
	@Mock
	ResultSet resultSet;
		
	@Test
	public void test() throws Exception {

		Mockito.when(conn.prepareStatement(Mockito.anyString())).thenReturn(prdStatement);
		Mockito.when(prdStatement.executeQuery()).thenReturn(resultSet);
		sequenceImpl.getNextValue(conn, "seqName");
	}
	
	@Test
	public void test2() throws Exception {
		
		Mockito.when(conn.prepareStatement(Mockito.anyString())).thenReturn(prdStatement);
		Mockito.when(prdStatement.executeQuery()).thenReturn(resultSet);
		Mockito.when(resultSet.next()).thenReturn(true);
		sequenceImpl.getNextValue(conn, "seqName");
	}

}
