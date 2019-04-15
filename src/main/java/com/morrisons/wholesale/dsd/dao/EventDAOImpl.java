package com.morrisons.wholesale.dsd.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

/**
 * Created by EXTMAS3P on 28/07/2017.
 */
public class EventDAOImpl implements EventDAO {

	@Autowired
	private AmazonDynamoDB amazonDynamoDBClient;

	public EventDAOImpl() {
		// default constructor...
	}

	public void setAmazonDynamoDBClient(AmazonDynamoDB amazonDynamoDBClient) {
		this.amazonDynamoDBClient = amazonDynamoDBClient;
	}

	@Override
	public void saveItem(String tableName, Item item) {
		Table table = new Table(this.amazonDynamoDBClient, tableName);
		table.putItem(item);
	}

	@Override
	public Item getItem(String tableName, GetItemSpec getItemSpec) {
		Table table = new Table(this.amazonDynamoDBClient, tableName);

		return table.getItem(getItemSpec);
	}

	@Override
	public Item getItem(String tableName, String hashKeyName, Object hashKeyValue) {
		Table table = new Table(this.amazonDynamoDBClient, tableName);
		return table.getItem(hashKeyName, hashKeyValue);
	}

	@Override
	public Item getItem(String tableName, String hashKeyName, Object hashKeyValue, String rangeKeyName,
			Object rangeKeyValue) {
		Table table = new Table(this.amazonDynamoDBClient, tableName);
		return table.getItem(hashKeyName, hashKeyValue, rangeKeyName, rangeKeyValue);
	}

	@Override
	public ItemCollection<QueryOutcome> query(String tableName, QuerySpec spec) {
		Table table = new Table(this.amazonDynamoDBClient, tableName);
		return table.query(spec);
	}
}
