package com.dynamicduo.service;

import com.dynamicduo.database.Patient;

import java.util.Map;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;

public class PatientService extends Service{

    public PatientService(String userId, String path, String httpMethod, Map<String,String> pathParameters, Map<String,String> queryStringParameters, JsonNode body){
        super(userId, path, httpMethod, pathParameters, queryStringParameters, body);
    }

    @Override
    protected void handleGetRequest(){
        try{
            Patient patient = new Patient().getUser(this.userId);
            if(patient != null){
                constructResponse(200, "Create Patient Successfully", null, patient);
            }else{
                constructResponse(404, null, "Patient Not Found", null);
            }
        }catch (Exception ex){
            LOG.error("error: {}", ex);
        }

    }
    
    @Override
    protected void handlePostRequest(){
        constructResponse(200, "In Create Patient", null, null);
        try{
            // Map<String, Object> attributes = new HashMap<>();

            // attributes.put("userId", body.get("userId"));
            // attributes.put("email", body.get("email"));
            // attributes.put("username", body.get("username"));
            // attributes.put("dateOfBirth", body.get("dateOfBirth").asText());
            // attributes.put("gender", body.get("gender").asText());
            // attributes.put("alexaPin", body.get("alexaPin").asText());
            // attributes.put("image", body.get("image").asText());






            // Patient patient = new Patient();
            // patient.save(attributes);
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
        try{
            Patient patient  = new Patient();
            Boolean result = patient.deleteUser(this.userId);
            if(result){
                Map<String, String> response = new HashMap<>();
                response.put("userId", this.userId);
                constructResponse(200, "Delete Patient Successfully", null, response);
            }else   constructResponse(404, null, "Patient Not Found", null);
        }catch (Exception ex){
            LOG.error("error: {}", ex);
        }

    }
}