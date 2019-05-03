package com.morrisons.wholesale.dsd.service.main;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.morrisons.wholesale.dsd.exception.IWMMExceptionFactory;
import com.morrisons.wholesale.dsd.vo.ProcessDetails;
import com.morrisons.wholesale.dsd.vo.SourceProcessingStatus;

/**
 * 
 * @author surajv
 *
 */
public class DummyBaseApplicationService extends BaseApplicationService {

	protected DummyBaseApplicationService(IWMMExceptionFactory exceptionFactory) {
		super(exceptionFactory);
	}

	@Override
	protected ProcessDetails getDetailsForProcessing() {
		return null;
	}

	@Override
	protected InputStream getFile(ProcessDetails pd) {
		return null;
	}

	@Override
	protected void persistFile(ProcessDetails pd, InputStream is) {
		
	}

	@Override
	protected void moveFileToError(ProcessDetails pd) {
		
	}

	@Override
	protected boolean manageDuplicates(ProcessDetails pd) {
		return false;
	}

	@Override
	protected boolean manageInvalidDataValues(ProcessDetails pd) {
		return false;
	}

	@Override
	protected List<String> getUniqueSources(ProcessDetails pd) {
		return null;
	}

	@Override
	protected SourceProcessingStatus processSource(ProcessDetails pd, String sourceId) {
		return null;
	}

	@Override
	protected boolean generateDistributionOrder(ProcessDetails pd, String sourceId) {
		return false;
	}

	@Override
	protected boolean cancelStockMovement(ProcessDetails pd, String sourceId) {
		return false;
	}

	@Override
	protected void moveFileToProcessed(ProcessDetails pd) {
		
	}

	@Override
	protected void truncateData(ProcessDetails pd) {
		
	}

	@Override
	protected List<Object[]> getErrorRecords(ProcessDetails pd) {
		return null;
	}

	@Override
	protected Map<String, List<String>> getMap(List<Object[]> result) {
		return null;
	}

	@Override
	protected void splitFile(Map<String, List<String>> sourceItems, ProcessDetails pd) {
		
	}

	@Override
	protected void moveActualFileToArchive(ProcessDetails pd) {
		
	}

	@Override
	protected void updateStausForRetry(ProcessDetails pd) {
		
	}

	@Override
	protected void deleteErrorRecords(String orderId) {
		// TODO Auto-generated method stub
		
	}
	
}