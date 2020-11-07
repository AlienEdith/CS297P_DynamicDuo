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
// import java.text.Attribute;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.time.Instant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jdk.jfr.Frequency;
import sun.font.AttributeValues;

@DynamoDBTable(tableName = "PLACEHOLDER")
public class Symptom {

    private static final String PATIENT_TABLE_NAME = System.getenv("PATIENT_TABLE");

    private static DynamoDBAdapter db_adapter;
    private final AmazonDynamoDB client;
    private final DynamoDBMapper mapper;

    private final Logger LOG = LogManager.getLogger(this.getClass());

    private String userId;
    private String recordTime;
    private String symptomId;
    private String symptomName;
    private String startDate;
    private String symptomLocation;
    private String symptomLocationDescription;
    private String symptomMovement;
    private String frequency;
    private String symptomConsistency;
    private String severity;
    private String symptomDescription;
    private String previousItemId;
    private String completionTime;
    private String reviewTime;
    private String resolvedDate;
    private String betterCondition;
    private String worseCondition;
    private String impactToLife;
    private String otherRelatedSymptom;

    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() { return this.userId; }
    public void setUserId(String userId) { this.userId = userId; }

    @DynamoDBRangeKey(attributeName = "recordTime")
    public String getRecordTime() { return this.recordTime; }
    public void setRecordTime(String recordTime) { this.recordTime = recordTime; }

    @DynamoDBAttribute(attributeName = "symptomId")
    public String getSymptomId() { return this.symptomId; }
    public void setSymptomId(String symptomId) { this.symptomId = symptomId; }

    @DynamoDBAttribute(attributeName = "symptomName")
    public String getSymptomName() { return this.symptomName; }
    public void setSymptomName(String symptomName) { this.symptomName = symptomName; }

    @DynamoDBAttribute(attributeName = "startDate")
    public String getStartDate() { return this.startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    @DynamoDBAttribute(attributeName = "symptomLocation")
    public String getSymptomLocation() { return this.symptomLocation; }
    public void setSymptomLocation(String symptomLocation) { this.symptomLocation = symptomLocation; }

    @DynamoDBAttribute(attributeName = "symptomLocationDescription")
    public String getSymptomLocationDescription() { return this.symptomLocationDescription; }
    public void setSymptomLocationDescription(String symptomLocationDescription) { this.symptomLocationDescription = symptomLocationDescription; }

    @DynamoDBAttribute(attributeName = "symptomMovement")
    public String getSymptomMovement() { return this.symptomMovement; }
    public void setSymptomMovement(String symptomMovement) { this.symptomMovement = symptomMovement; }
    
    @DynamoDBAttribute(attributeName = "frequency")
    public String getFrequency() { return this.frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }

    @DynamoDBAttribute(attributeName = "symptomConsistency")
    public String getSymptomConsistency() { return this.symptomConsistency; }
    public void setSymptomConsistency(String symptomConsistency) { this.symptomConsistency = symptomConsistency; }
    
    @DynamoDBAttribute(attributeName = "severity")
    public String getSeverity() { return this.severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    
    @DynamoDBAttribute(attributeName = "symptomDescription")
    public String getSymptomDescription() { return this.symptomDescription; }
    public void setSymptomDescription(String symptomDescription) { this.symptomDescription = symptomDescription; }

    @DynamoDBAttribute(attributeName = "previousItemId")
    public String getPreviousItemId() { return this.previousItemId; }
    public void setPreviousItemId(String previousItemId) { this.previousItemId = previousItemId; }

    @DynamoDBAttribute(attributeName = "completionTime")
    public String getCompletionTime() { return this.completionTime; }
    public void setCompletionTime(String completionTime) { this.completionTime = completionTime; }

    @DynamoDBAttribute(attributeName = "reviewTime")
    public String getReviewTime() { return this.reviewTime; }
    public void setReviewTime(String reviewTime) { this.reviewTime = reviewTime; }
    
    @DynamoDBAttribute(attributeName = "resolvedDate")
    public String getResolvedDate() { return this.resolvedDate; }
    public void setResolvedDate(String resolvedDate) { this.resolvedDate = resolvedDate; }

    @DynamoDBAttribute(attributeName = "betterCondition")
    public String getBetterCondition() { return this.betterCondition; }
    public void setBetterCondition(String betterCondition) { this.betterCondition = betterCondition; }

    @DynamoDBAttribute(attributeName = "worseCondition")
    public String getWorseCondition() { return this.worseCondition; }
    public void setWorseCondition(String worseCondition) { this.worseCondition = worseCondition; }

    @DynamoDBAttribute(attributeName = "impactToLife")
    public String getImpactToLife() { return this.impactToLife; }
    public void setImpactToLife(String impactToLife) { this.impactToLife = impactToLife; }

    @DynamoDBAttribute(attributeName = "otherRelatedSymptom")
    public String getOtherRelatedSymptom() { return this.otherRelatedSymptom; }
    public void setOtherRelatedSymptom(String otherRelatedSymptom) { this.otherRelatedSymptom = otherRelatedSymptom; }



    public Symptom() {
        DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder()
            .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(PATIENT_TABLE_NAME))
            .build();
        this.db_adapter = DynamoDBAdapter.getInstance();
        this.client = this.db_adapter.getDbClient();
        this.mapper = this.db_adapter.createDbMapper(mapperConfig);
    }

    public String toString() {
        return String.format("Symptom [id=%s]", this.userId);
    }

    public Symptom save() throws IOException {
        this.mapper.save(this);
        return get(this.userId, this.recordTime);
    }
    public Symptom get(String userId, String recordTime) throws IOException {
        return this.mapper.load(Symptom.class, userId, recordTime);
    }
    public boolean delete(String userId, String recordTime) throws IOException {
        Symptom symptom = get(userId, recordTime);
        this.mapper.delete(symptom);

        return true;
    }
    // public boolean completeSymptom(String userId, String recordTime, HashMap<String, AttributeValue> params) throws IOException{
    //     Symptom symptom = getOneSymptom(userId, recordTime);
    //     //TODO
    //     return true;

    // }
    
    public Symptom update() throws IOException{
        return save();
    }
    // public Symptom getOneSymptom(String userId, String recordTime) throws IOException{
    //     return this.mapper.load(Symptom.class, userId, recordTime);
    // }
    public PaginatedQueryList<Symptom> getSymptoms(String userId, String lastDays, String resovledState, String sortBy) throws IOException{
        Long curDayEpoch = new Date().getTime();
        Long startDayEpoch = lastDays.equals("") ? 1L : curDayEpoch - 86400 * Integer.parseInt(lastDays);
        HashMap<String, AttributeValue> attributes = new HashMap<String, AttributeValue>();
        attributes.put(":uId", new AttributeValue().withS(userId));
        attributes.put(":rT", new AttributeValue().withS("SYMPTOM" + startDayEpoch.toString()));
        DynamoDBQueryExpression<Symptom> queryExpression = new DynamoDBQueryExpression<Symptom>()
        .withKeyConditionExpression("userId = :uId and recordTime > :rT").withExpressionAttributeValues(attributes);

        PaginatedQueryList<Symptom> symptoms = this.mapper.query(Symptom.class, queryExpression);
        return symptoms;
    }

}
