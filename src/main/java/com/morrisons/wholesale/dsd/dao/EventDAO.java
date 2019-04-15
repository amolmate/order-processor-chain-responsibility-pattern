package com.morrisons.wholesale.dsd.dao;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

/**
 * Created by EXTMAS3P on 28/07/2017.
 */
public interface EventDAO {

	public void saveItem(String tableName, Item item);

	public Item getItem(String tableName, GetItemSpec getItemSpec);

	public Item getItem(String tableName, String hashKeyName, Object hashKeyValue);

	public Item getItem(String tableName, String hashKeyName, Object hashKeyValue, String rangeKeyName,
			Object rangeKeyValue);

	public ItemCollection<QueryOutcome> query(String tableName, QuerySpec spec);
}