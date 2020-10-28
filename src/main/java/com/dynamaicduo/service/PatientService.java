package com.dynamicduo.service;

import com.dynamicduo.database.Patient;

import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;

public class PatientService extends Service{

    public PatientService(String userId, String path, String httpMethod, Map<String,String> pathParameters, Map<String,String> queryStringParameters, JsonNode body){
        super(userId, path, httpMethod, pathParameters, queryStringParameters, body);
    }

    @Override
    protected void handleGetRequest(){
        try{
            Patient patient = new Patient().get(this.userId);
            this.result = patient;
        }catch (Exception ex){
            LOG.error("error: {}", ex);
        }

    }
    
    @Override
    protected void handlePostRequest(){
        try{
            Map<String, Object> attributes = new HashMap<>();

            // attributes.put("userId", body.get("userId").asText());
            // attributes.put("email", body.get("email").asText());
            // attributes.put("username", body.get("username").asText());
            // attributes.put("dateOfBirth", body.get("dateOfBirth").asText());
            // attributes.put("gender", body.get("gender").asText());
            // attributes.put("chronicCondition", body.get("alexaPin").asText());
            // attributes.put("userId", body.get("userId").asText());
            // attributes.put("userId", body.get("userId").asText());




            Patient patient = new Patient();
            patient.save();
        }catch (Exception ex){
            LOG.error("error: {}", ex);
        }
    }

    @Override
    protected void handlePutRequest(){

    }

    @Override
    protected void handlePatchRequest(){

    }

    @Override
    protected void handleDeleteRequest(){

    }
}