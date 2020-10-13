package com.dynamicduo.database;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@DynamoDBTable(tableName = "PLACEHOLDER_PATIENT_TABLE_NAME")
public class Patient {

    private static final String PATIENT_TABLE_NAME = System.getenv("PATIENT_TABLE");

    private static DynamoDBAdapter db_adapter;
    private final AmazonDynamoDB client;
    private final DynamoDBMapper mapper;

    private final Logger LOG = LogManager.getLogger(this.getClass());
    // private Logger logger = Logger.getLogger(this.getClass());

    private String userId;
    private String recordTime;

    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDBRangeKey(attributeName = "recordTime")
    public String getRecordTime() {
        return this.recordTime;
    }
    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    // @DynamoDBAttribute(attributeName = "price")
    // public Float getPrice() {
    //     return this.price;
    // }
    // public void setPrice(Float price) {
    //     this.price = price;
    // }

    public Patient() {
        DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder()
            .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(PATIENT_TABLE_NAME))
            .build();
        this.db_adapter = DynamoDBAdapter.getInstance();
        this.client = this.db_adapter.getDbClient();
        this.mapper = this.db_adapter.createDbMapper(mapperConfig);
    }

    public String toString() {
        return String.format("Patient [id=%s]", this.userId);
    }

    // methods
    public Boolean ifTableExists() {
        return this.client.describeTable(PATIENT_TABLE_NAME).getTable().getTableStatus().equals("ACTIVE");
    }

    // public List<Product> list() throws IOException {
    //   DynamoDBScanExpression scanExp = new DynamoDBScanExpression();
    //   List<Product> results = this.mapper.scan(Product.class, scanExp);
    //   for (Product p : results) {
    //     logger.info("Products - list(): " + p.toString());
    //   }
    //   return results;
    // }

    public Patient get(String userId) throws IOException {
        Patient patient = null;

        HashMap<String, AttributeValue> attributeValue = new HashMap<String, AttributeValue>();
        attributeValue.put(":userId", new AttributeValue().withS(userId));
        attributeValue.put(":recordTime", new AttributeValue().withS("PATIENT"));

        DynamoDBQueryExpression<Patient> queryExp = new DynamoDBQueryExpression<Patient>()
            .withKeyConditionExpression("userId = :userId and recordTime = :recordTime")
            .withExpressionAttributeValues(attributeValue);

        PaginatedQueryList<Patient> result = this.mapper.query(Patient.class, queryExp);
        if (result.size() > 0) {
          patient = result.get(0);
          LOG.info("patient - get(): patient - " + patient.toString());
        } else {
          LOG.info("patient - get(): patient - Not Found.");
        }
        return patient;
    }

    public void save(Patient patient) throws IOException {
        // logger.info("Products - save(): " + product.toString());
        this.mapper.save(patient);
    }

    // public Boolean delete(String id) throws IOException {
    //     Product product = null;

    //     // get product if exists
    //     product = get(id);
    //     if (product != null) {
    //       logger.info("Products - delete(): " + product.toString());
    //       this.mapper.delete(product);
    //     } else {
    //       logger.info("Products - delete(): product - does not exist.");
    //       return false;
    //     }
    //     return true;
    // }

}
