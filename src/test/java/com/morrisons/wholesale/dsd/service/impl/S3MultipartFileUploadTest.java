package com.morrisons.wholesale.dsd.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.amazonaws.services.s3.model.UploadPartResult;
import com.morrisons.wholesale.dsd.config.ApplicationConfig;
import com.morrisons.wholesale.dsd.config.S3FileConfiguration;
import com.morrisons.wholesale.dsd.exception.IWMMExceptionFactory;
import com.morrisons.wholesale.dsd.exception.WMMException;
import com.morrisons.wholesale.dsd.s3.IS3ClientAdapter;
import com.morrisons.wholesale.dsd.s3.IS3MultipartFileUploadClientAdapter;
import com.morrisons.wholesale.dsd.vo.ProcessDetails;

@RunWith(MockitoJUnitRunner.class)
public class S3MultipartFileUploadTest {

	@InjectMocks
	@Spy
	private S3MultipartFileUpload s3MultipartFileUpload;

	@Mock
	private IWMMExceptionFactory exceptionFactory;

	@Mock
	private ApplicationConfig applicationConfig;

	@Mock
	private IS3ClientAdapter s3ClientAdapter;

	@Mock
	private IS3MultipartFileUploadClientAdapter s3MultipartFileUploadClientAdapter;

	private ProcessDetails processDetails = new ProcessDetails();

	private List<String> items = new ArrayList<>();

	private Map<String, List<String>> sourceItems = new HashMap<>();

	@Before
	public void init() {
		Mockito.when(exceptionFactory.createException(Mockito.anyInt(), Mockito.any(Exception.class)))
				.thenReturn(Mockito.mock(WMMException.class));

		processDetails.setBucket("bucket");
		processDetails.setFileKey("fileKey");

		items.add("3");

		sourceItems.put("1", items);
		sourceItems.put("2", items);

		InputStream value1 = new ByteArrayInputStream("1,2,3,4,1".getBytes());
		Mockito.when(s3ClientAdapter.getFile(Mockito.anyString(), Mockito.anyString())).thenReturn(value1);

		S3FileConfiguration fileConfig = new S3FileConfiguration();
		fileConfig.setFolder("folder");
		fileConfig.setBucket("bucket");
		fileConfig.setFileName("filename");
		fileConfig.setFileExtn("fileExt");

		Mockito.when(applicationConfig.getProcessedLocationConfig()).thenReturn(fileConfig);
		Mockito.when(applicationConfig.getErrorLocationConfig()).thenReturn(fileConfig);
		Mockito.when(applicationConfig.getThreadPoolSize()).thenReturn(1);
	}

	@Test
	public void processMultipartFileUpload() {

		s3MultipartFileUpload.processMultipartFileUpload(sourceItems, processDetails);
	}

	@Test
	public void processMultipartFileUpload2() {

		Mockito.when(applicationConfig.getMinMBForS3Upload()).thenReturn(4);
		s3MultipartFileUpload.processMultipartFileUpload(sourceItems, processDetails);
	}

	@Test
	public void processMultipartFileUploadWithErrorRecords() {

		items.remove(0);
		items.add("7");
		s3MultipartFileUpload.processMultipartFileUpload(sourceItems, processDetails);
	}

	@Test
	public void processMultipartFileUploadWithErrorRecords2() {

		items.remove(0);
		items.add("7");
		Mockito.when(applicationConfig.getMinMBForS3Upload()).thenReturn(4);
		s3MultipartFileUpload.processMultipartFileUpload(sourceItems, processDetails);
	}

	@Test(expected = Exception.class)
	public void processMultipartFileUpload_throws_exception_test() {

		s3MultipartFileUpload.processMultipartFileUpload(sourceItems, null);

	}

	@Test(expected = Exception.class)
	public void processFileSplitAndUpload_throws_exception_test() throws Exception {
		Map<String, List<String>> sourceItems = new HashMap<>();
		ProcessDetails processDetails = new ProcessDetails();

		List<String> list = new ArrayList<>();
		list.add("ALL");

		sourceItems.put("ALL", list);

		processDetails.setBucket("bucket");
		processDetails.setFileKey("filekey");

		S3FileConfiguration value = new S3FileConfiguration();
		value.setFolder("folder");
		value.setBucket("folder");
		value.setFileName("folder");
		value.setFileExtn("folder");

		Mockito.when(applicationConfig.getProcessedLocationConfig()).thenReturn(value);
		Mockito.when(applicationConfig.getErrorLocationConfig()).thenReturn(value);

		InputStream value1 = new ByteArrayInputStream("1,2,3,4,ALL".getBytes());
		Mockito.when(s3ClientAdapter.getFile(Mockito.anyString(), Mockito.anyString())).thenReturn(value1);
		Mockito.when(applicationConfig.getMinMBForS3Upload()).thenReturn(5);

		Method method = S3MultipartFileUpload.class.getDeclaredMethod("processFileSplitAndUpload", Map.class,
				InputStream.class, String.class, String.class, ProcessDetails.class, String.class, String.class);
		method.setAccessible(true);
		method.invoke(s3MultipartFileUpload, null, value1, "uploadSuccessId", "uploadErrorId", processDetails,
				"processFileLocation1", "processFileLocation");
	}

	@Test(expected = Exception.class)
	public void completeMultipartUpload_throws_exception_test() throws Exception {

		Set<Future<UploadPartResult>> uploadStandardPartsCallableSuccessSet = new HashSet<>();
		Future<UploadPartResult> future = null;
		uploadStandardPartsCallableSuccessSet.add(future);

		Method method = S3MultipartFileUpload.class.getDeclaredMethod("completeMultipartUpload", Set.class, Set.class,
				ExecutorService.class, String.class, String.class, String.class, String.class, String.class);
		method.setAccessible(true);
		method.invoke(s3MultipartFileUpload, uploadStandardPartsCallableSuccessSet, null, null, "uploadSuccessId",
				"uploadErrorId", "processDetails", "processFileLocation1", "processFileLocation");
	}

	@Test(expected = Exception.class)
	public void completeMultipartUpload2_throws_exception_test() throws Exception {

		Set<Future<UploadPartResult>> uploadStandardPartsCallableSuccessSet = new HashSet<>();
		@SuppressWarnings("unchecked")
		Future<UploadPartResult> future = Mockito.mock(Future.class);
		uploadStandardPartsCallableSuccessSet.add(future);

		Set<Future<UploadPartResult>> uploadStandardPartsCallableErrorSet = new HashSet<>();
		Future<UploadPartResult> future1 = null;
		uploadStandardPartsCallableErrorSet.add(future1);

		Method method = S3MultipartFileUpload.class.getDeclaredMethod("completeMultipartUpload", Set.class, Set.class,
				ExecutorService.class, String.class, String.class, String.class, String.class, String.class);
		method.setAccessible(true);
		method.invoke(s3MultipartFileUpload, uploadStandardPartsCallableSuccessSet, uploadStandardPartsCallableErrorSet,
				null, "uploadSuccessId", "uploadErrorId", "processDetails", "processFileLocation1",
				"processFileLocation");
	}
}
