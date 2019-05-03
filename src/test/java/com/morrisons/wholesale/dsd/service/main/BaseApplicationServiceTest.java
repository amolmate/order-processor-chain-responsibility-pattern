package com.morrisons.wholesale.dsd.service.main;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.morrisons.wholesale.dsd.exception.IWMMExceptionFactory;
import com.morrisons.wholesale.dsd.exception.WMMException;
import com.morrisons.wholesale.dsd.vo.FileUtil;
import com.morrisons.wholesale.dsd.vo.ProcessDetails;
import com.morrisons.wholesale.dsd.vo.SourceProcessingStatus;

/**
 * 
 * @author surajv
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BaseApplicationServiceTest {

	@Spy
	@InjectMocks
	private DummyBaseApplicationService dummyBaseApplicationService;

	@Mock
	private IWMMExceptionFactory exceptionFactory;

	@Mock
	private WMMException exception;

	@Mock
	private ProcessDetails processDetails;
	
	@Mock
	private FileUtil fileUtil;

	@Before
	public void setUp() {

		Mockito.when(exceptionFactory.createException(Matchers.anyInt(), Matchers.anyString(), Matchers.any()))
				.thenReturn(exception);

		List<String> value = new ArrayList<>();
		value.add("A");
		Mockito.when(dummyBaseApplicationService.getUniqueSources(Matchers.any())).thenReturn(value );
		Mockito.when(dummyBaseApplicationService.processSource(Matchers.any(),Matchers.anyString())).thenReturn(SourceProcessingStatus.GENERATE_DO);
	}

	@Test
	public void testProcess() {

		dummyBaseApplicationService.process();
		
		Mockito.verify(dummyBaseApplicationService, Mockito.times(1)).getFile(Matchers.any());
		Mockito.verify(dummyBaseApplicationService, Mockito.times(1)).persistFile(Matchers.any(), Matchers.any());
		Mockito.verify(dummyBaseApplicationService, Mockito.times(1)).truncateData(Matchers.any());
		
	/*	Mockito.when(dummyBaseApplicationService.processSource(Matchers.any(),Matchers.anyString())).thenReturn(SourceProcessingStatus.CANCEL_STOCK_MOVEMENT);
		Mockito.when(fileUtil.getFileName(Matchers.any())).thenReturn("A");
		dummyBaseApplicationService.process();*/	
	}
}