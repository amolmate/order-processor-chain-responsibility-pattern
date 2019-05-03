package com.morrisons.wholesale.dsd.service.main.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.morrisons.wholesale.dsd.config.ApplicationConfig;
import com.morrisons.wholesale.dsd.config.S3FileConfiguration;
import com.morrisons.wholesale.dsd.dao.IStockMovementSTGDAO;
import com.morrisons.wholesale.dsd.dao.IStockMovementSTGErrorDAO;
import com.morrisons.wholesale.dsd.dao.IStockMovementSTGRetryDAO;
import com.morrisons.wholesale.dsd.exception.IWMMExceptionFactory;
import com.morrisons.wholesale.dsd.service.IPreProcessStockDataService;
import com.morrisons.wholesale.dsd.service.IS3StockFileService;
import com.morrisons.wholesale.dsd.service.IStockDataService;
import com.morrisons.wholesale.dsd.service.IStockMovementService;
import com.morrisons.wholesale.dsd.service.ITransformer;
import com.morrisons.wholesale.dsd.service.file.IS3DownloadFileService;
import com.morrisons.wholesale.dsd.service.file.IS3MultipartFileUpload;
import com.morrisons.wholesale.dsd.util.Util;
import com.morrisons.wholesale.dsd.vo.ProcessDetails;
import com.morrisons.wholesale.dsd.vo.ServiceCallStatus;
import com.morrisons.wholesale.dsd.vo.SourceProcessingStatus;

/**
 * 
 * @author surajv
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ApplicationServiceTest {

	@InjectMocks
	private ApplicationService applicationService;

	@Mock
	private ApplicationConfig applicationConfig;

	@Mock
	private ITransformer transformer;

	@Mock
	private S3FileConfiguration s3File;

	@Mock
	private IS3DownloadFileService<InputStream> downloadFileService;

	@Mock
	private IStockMovementSTGDAO stockMovementSTGDAO;

	@Mock
	private IStockDataService processStockFileService;

	@Mock
	private IStockMovementService stockMovementService;

	@Mock
	private InputStream inStream;

	@Mock
	private IS3StockFileService s3StockFileService;

	private ProcessDetails processDetails;

	private List<ServiceCallStatus> responses = new ArrayList<>();
	
	private List<Object[]> objList;
	
	@Mock
	private IWMMExceptionFactory exception;
	
	@Mock
	private IPreProcessStockDataService preProcessDuplicatesSvc;
	
	@Mock
	private IPreProcessStockDataService preProcessIncorrectRecSvc;
	
	@Mock
	private IStockMovementSTGErrorDAO stockMovementSTGErrorDAO;
	
	@Mock
	private IS3MultipartFileUpload s3MultipartFileUpload;
	
	@Mock
	private IStockMovementSTGRetryDAO stockMovementSTGRetryDAO;

	@Before
	public void setUp() throws Exception {

		Util.setENVVariable("X");
		Long jobId = 1L;

		processDetails = new ProcessDetails();
		processDetails.setJobId(jobId);
		processDetails.setBucket("B");
		processDetails.setFileKey("K");
		

		Mockito.when(s3File.getFolder()).thenReturn("X");
		Mockito.when(s3File.getFolder()).thenReturn("B");
		Mockito.when(s3File.getFolder()).thenReturn("F");
		Mockito.when(s3File.getFolder()).thenReturn("E");
		
		processDetails.setOrderId("X");
		objList = new ArrayList<>();
		Object[] obj = new Object[2];
		obj[0] = "X";
		obj[1] = "A";
		objList.add(obj);

		Mockito.when(downloadFileService.getData(Matchers.any(), Matchers.any())).thenReturn(inStream);

		responses.add(ServiceCallStatus.SUCCESS);
	}

	@After
	public void tearDown() throws Exception {

		Util.setENVVariable(null);
	}

	@Test
	public void testGetFileDetails() {

		Assert.assertNotNull("ProcessDetail is Null", applicationService.getDetailsForProcessing());
	}

	@Test
	public void testGetFile() {

		Assert.assertNotNull("InputStream is Null", applicationService.getFile(processDetails));
	}

	@Test
	public void testPersistFile() {

		applicationService.persistFile(processDetails, inStream);

		Mockito.verify(stockMovementSTGDAO, Mockito.times(1)).copyStockToDB(Matchers.anyLong(), Matchers.any());
	}

	@Test
	public void testTruncateData() {

		applicationService.truncateData(processDetails);

		Mockito.verify(stockMovementSTGDAO, Mockito.times(1)).deleteProcessedStock(Matchers.anyLong());
	}

	@Test
	public void testMoveFile() {
		applicationService.moveFileToProcessed(processDetails);
		Mockito.verify(s3StockFileService, Mockito.times(1)).moveProcessedFile(processDetails);
	}

	@Test
	public void testProcessData() {

		// List<StockMovementResponseDetail> processData = new ArrayList<>();
		// processData.add(new StockMovementResponseDetail());
		// Mockito.when(processStockFileService.processData(Matchers.any(),
		// Matchers.any(), Matchers.any()))
		// .thenReturn(responses);
		//
		// applicationService.processData(processDetails, "3");
		// Assert.assertNotNull("Method should result a list",
		// processStockFileService.processData(Matchers.any(), Matchers.any(),
		// Matchers.any()));
	}

	@Test
	public void testMoveFailedFile() {

		applicationService.moveFileToError(processDetails);
		Mockito.verify(s3StockFileService, Mockito.times(1)).moveFailedFile(processDetails);
	}
	
	@Test
	public void testManageDuplicates(){
		
		Mockito.when(preProcessDuplicatesSvc.process(Matchers.anyLong(), Matchers.anyString(), Matchers.anyString())).thenReturn(objList);
		applicationService.manageDuplicates(processDetails);
	}
	
	@Test
	public void testmanageInvalidDataValues(){
		
		Mockito.when(preProcessIncorrectRecSvc.process(Matchers.anyLong(), Matchers.anyString(), Matchers.anyString())).thenReturn(objList);
		applicationService.manageInvalidDataValues(processDetails);
	}
	
	@Test
	public void testgetUniqueSources(){
		
		Mockito.when(processStockFileService.getUniqueSources(Matchers.anyLong())).thenReturn(Matchers.anyList());
	    Assert.assertNotNull(applicationService.getUniqueSources(processDetails));
	}
	
	@Test
	public void testprocessSource(){
		
		Mockito.when(processStockFileService.processData(Matchers.any(), Matchers.anyString())).thenReturn(SourceProcessingStatus.PENDING);
		Assert.assertNotNull(applicationService.processSource(processDetails, "X"));
	}
	
	@Test
	public void testgenerateDistributionOrder(){
		
		Mockito.when(stockMovementService.generateDistributionOrder(Matchers.anyString(), Matchers.anyString(), Matchers.any())).thenReturn(true);
		Assert.assertTrue(applicationService.generateDistributionOrder(processDetails, "X"));
	}
	
	@Test
	public void testcancelStockMovement(){
		
		Mockito.when(stockMovementService.cancelStockMovement(Matchers.anyString(), Matchers.anyString(), Matchers.any())).thenReturn(true);
		Assert.assertTrue(applicationService.cancelStockMovement(processDetails, "X"));
	}
	
	@Test
	public void testgetErrorRecords(){
		
		Mockito.when(stockMovementSTGErrorDAO.fetchErrorRecords(Matchers.anyString())).thenReturn(objList);
		Assert.assertNotNull(applicationService.getErrorRecords(processDetails));
	}
	
	@Test
	public void testgetMap(){
		
		Object[] obj = new Object[2];
		obj[0] = "X";
		obj[1] = "A";
		objList.add(obj);
		Assert.assertNotNull(applicationService.getMap(objList));
	}
	
	@Test
	public void testsplitFile(){
		
		Map<String, List<String>> sourceItems = new HashMap<>();
		applicationService.splitFile(sourceItems , processDetails);
	}
	
	@Test
	public void testmoveActualFileToArchive(){
		applicationService.moveActualFileToArchive(processDetails);
	}
	
	@Test
	public void testdeleteErrorRecords(){
		applicationService.deleteErrorRecords("X");
	}
	
	@Test
	public void testupdateStausForRetry(){
		processDetails.setOrderId("A");
		applicationService.updateStausForRetry(processDetails);
	}
}