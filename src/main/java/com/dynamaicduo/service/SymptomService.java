package com.dynamicduo.service;

import java.util.Map;
import com.dynamicduo.database.Symptom;

import java.util.Date;

import com.fasterxml.jackson.databind.JsonNode;

import com.google.gson.Gson;

public class SymptomService extends Service{

    public SymptomService(String userId, String path, String httpMethod, Map<String,String> pathParameters, Map<String,String> queryStringParameters, String body){
        super(userId, path, httpMethod, pathParameters, queryStringParameters, body);
    }

    @Override
    protected void handleGetRequest(){
        LOG.info("handle in service: {}", "symptom");
        if(pathParameters != null &&  pathParameters.containsKey("recordTime")){
            // Get One Symptom

        }else{
            // Get Symptoms: condition and other requirements as query strings 
            String condition = queryStringParameters.getOrDefault("conditions", "");
            switch(condition){
                case "":
                    // Get All Symptoms
                    break;
                case "notcompleted":
                    // Get Incomplete Symptoms
                    break;
                case "review":
                    // Get Incomplete Symptoms
                    break;
                default:
                    break;
            }

        }
    }

    @Override
    protected void handlePostRequest(){
        // create symptom
        String recordTime = "SYMPTOM" + pathParameters.getOrDefault("recordTime", String.valueOf(new Date().getTime()));
        String completionTime = pathParameters.getOrDefault("completionTime", String.valueOf(Integer.parseInt(recordTime) + 43200));
        try{

            Gson gson = new Gson();
            Symptom symptom = gson.fromJson(this.body, Symptom.class);
            symptom.setRecordTime(recordTime);
            symptom.setCompletionTime(completionTime);
            Symptom returnedSymptom = symptom.save();

            if(returnedSymptom != null){
                responseData.put("symptom", returnedSymptom);
                constructResponse(200, "Create Symptom Successfully", null);
            }else   constructResponse(401, null, "Create Symptom Failed");

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