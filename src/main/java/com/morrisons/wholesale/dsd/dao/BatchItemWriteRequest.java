package com.morrisons.wholesale.dsd.dao;

import java.util.Collection;

import com.amazonaws.services.dynamodbv2.document.Item;

import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class BatchItemWriteRequest {

	protected BatchItemWriteRequest() {
		
	}

	private String tableName;

	@Getter(AccessLevel.NONE)
	private ThreadGroup threadGroup = new ThreadGroup("BulkItemRequestThreadGroup" + Math.random());

	@Getter(AccessLevel.NONE)
	private Collection<Item> items = null;

	public static BatchItemWriteRequest newBuilder() {
		return new BatchItemWriteRequest();
	}

	public BatchItemWriteRequest withItems(Collection<Item> items) {
		this.items = items;
		return this;
	}

	public BatchItemWriteRequest withTable(String tableName) {
		this.tableName = tableName;
		return this;
	}

	public BatchItemWriteRequest build() {
		return this;
	}

	public void waitForCompletingRequest() {
		while (this.threadGroup.activeCount() != 0) {
			continue;
		}
	}

	public ThreadGroup getAssociatedBatchGroup() {
		return threadGroup;
	}

}