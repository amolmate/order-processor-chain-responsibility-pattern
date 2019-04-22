package com.morrisons.wholesale.dsd.constant;

public class Constants {
	
	private Constants() {
		
	}

	public static final String VALIDATION_ERROR_MANUAL = "validation-error-manual";

	public static final String VALIDATION_ERROR_AUTO = "validation-error-auto";
	
	public static final String CATALOGUE_REDIS_CACHE_KEY = "catalogueRedisCacheKey";

	public static final String IS_BLANK_FIELDS_EXIST = "isNonMandatoryBlankFieldsExist";

	public static final String HYPHEN = "-";
	
	public static final String MESSAGE_GROUP_ID = "messageGroup1";
	
	public static final String CATALOGUE_ITEMS_KEY = "catalogueItemsKey";

	//Config Table Fields
	public static final String IS_PRODUCT_DESC_ENRICH_REQUIRED = "isProductDescEnrichRequired";

	public static final String CUSTOMER_ORDER_ID = "customerOrderId";

	public static final String NON_MANDATORY_FIELDS = "nonMandatoryBlankFields";

	public static final String HEADER = "header";

	public static final String DETAIL = "detail";

	public static final String MAP_FIELD = "mapField";

	public static final String DEFAULT_VALUE = "defaultValue";

	public static final String PRE_ENRICHMENT_CONFIG = "preEnrichmentConfig";

	public static final String ORDER_REFERENCE_CODE = "orderReferenceCode";

	public static final String ORDER_REFERENCE = "orderreference";

	public static final String SHIP_TO_LOCATION_ID = "shipToLocationId";

	public static final String MESSAGE_CREATED_AT = "messageCreatedAt";

	public static final String ORDER_RAISED_BY = "orderRaisedBy";

	public static final String ROLLOVER_OPPORTUNITY_STATUS = "rolloverOpportunityStatus";

	public static final String ORIGINAL_SHIPMENT_DATE = "originalShipmentDate";

	public static final String IS_ROLLOVER = "isRollover";

	public static final String SHIP_TO_DELIVER_AT = "shipToDeliverAt";

	public static final String SHIP_TO_DELIVER_LATEST_AT = "shipToDeliverLatestAt";
	
	public static final String INTERNAL_ROLLOVER_OPPORTUNITY_MAP ="internalRolloverOpportunityMap";
	
	public static final String EXTERNAL_ROLLOVER_OPPORTUNITY_MAP = "externalRolloverOpportunityMap";
	
	public static final String PRE_ENRICH_STORE_REVISION_ID = "preEnrichStoreRevisionId";
	
	public static final String SHIP_TO_PARTY_ID = "shipToPartyId";
	
	public static final String ORDER_ID = "orderId";

	//Item level fields
	public static final String ITEM_BASE_TYPE = "itemBaseType";
	public static final String ITEM_DESCRIPTION = "itemDescription";
	public static final String ITEM_CASE_SIZE = "itemCaseSize";
	public static final String SKU_PIN = "skuPin";
	public static final String BARCODE_EAN = "barcodeEan";
	public static final String CLIENT_ID = "clientId";
	public static final String ITEM_ALTERNATE_ID = "itemAlternateId";
	public static final String ITEM_LINE_ID = "itemLineId";
	public static final String ITEM_ID = "itemId";
	public static final String SKU_LEGACY = "skuLegacy";
	public static final String SKU_MIN = "skuMin";

}