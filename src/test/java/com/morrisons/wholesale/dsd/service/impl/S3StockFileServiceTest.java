package com.morrisons.wholesale.dsd.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.morrisons.wholesale.dsd.config.S3FileConfiguration;
import com.morrisons.wholesale.dsd.s3.IS3ClientAdapter;
import com.morrisons.wholesale.dsd.vo.ProcessDetails;

@RunWith(MockitoJUnitRunner.class)
public class S3StockFileServiceTest {

	@Spy
	@InjectMocks
	private S3StockFileService s3StockFileService;

	@Mock
	private IS3ClientAdapter s3ClientAdapter;

	@Mock
	private S3FileConfiguration s3ProcessedFileConfig;

	@Mock
	private S3FileConfiguration s3ErrorFileConfig;

	private ProcessDetails processDetails;

	@Before
	public void setUp() {

		processDetails = new ProcessDetails();
		processDetails.setBucket("X");
		processDetails.setFileKey("Y");
		processDetails.setJobId(1L);

	}

	@Test
	public void moveTest() {
		
		S3StockFileService s3StockFileService = new S3StockFileService(null,null,null,null) {
			protected void move(ProcessDetails processDetails, S3FileConfiguration s3FileConfig) {
				
			}
		};
		s3StockFileService.moveArchivedFile(null);
		s3StockFileService.moveProcessedFile(null);

//		//StockMovementResponseDetail responseDetail = new StockMovementResponseDetail();
//		responseDetail.setAccepted(true);
//		List<StockMovementResponseDetail> responses = new ArrayList<>();
//		responses.add(responseDetail);
//
//		s3StockFileService.moveProcessedFile(processDetails, responses, 1);
//		Mockito.verify(s3StockFileService, Mockito.times(1)).moveProcessedFile(processDetails, responses, 1);

	}

	@Test
	public void negativeMoveTest() {
//		StockMovementResponseDetail responseDetail = new StockMovementResponseDetail();
//		responseDetail.setAccepted(false);
//		List<StockMovementResponseDetail> responses = new ArrayList<>();
//		responses.add(responseDetail);
//		s3StockFileService.moveProcessedFile(processDetails, responses, 0);
//		Mockito.verify(s3StockFileService, Mockito.times(1)).moveProcessedFile(processDetails, responses, 0);

	}

	@Test
	public void testMoveFailedFile() {
		s3StockFileService.moveFailedFile(processDetails);
		Mockito.verify(s3StockFileService, Mockito.times(1)).moveFailedFile(processDetails);

	}
}
