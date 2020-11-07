package com.dynamicduo.service;

import java.util.Map;
import com.dynamicduo.database.Symptom;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;

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
                    String lastDays = queryStringParameters.getOrDefault("lastDays","");
                    String reslovedState = queryStringParameters.getOrDefault("resolvedState","");
                    String sortBy = queryStringParameters.getOrDefault("sortBy","");
                    try {
                        Symptom symptom = new Symptom();
                        PaginatedQueryList<Symptom> symptoms = symptom.getSymptoms(userId, lastDays, reslovedState, sortBy);
                        if(symptoms != null){
                            responseData.put("symptoms", symptoms);
                            constructResponse(200, "Get Symptoms Successfully", null);
                        }else   constructResponse(401, null, "Get Symptoms Failed");
                    }catch(Exception ex) {
                        LOG.error("error: {}", ex);
                    }
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
       
        // LOG.info("recordTime: {}" + recordTime);
        try{

            Gson gson = new Gson();
            Symptom symptom = gson.fromJson(this.body, Symptom.class);
             // create symptom
            String recordTime;
            String completionTime;
            recordTime = "SYMPTOM#" + (symptom.getRecordTime().equals("") ?  String.valueOf(new Date().getTime()) : symptom.getRecordTime());
            completionTime = symptom.getCompletionTime().equals("") ? String.valueOf(Integer.parseInt(recordTime) + 43200) : symptom.getCompletionTime();
            symptom.setRecordTime(recordTime);
            symptom.setCompletionTime(completionTime);
            symptom.setUserId(userId);
            LOG.info("symptom: {}" + symptom.toString());

            // LOG.info("symptom: " + symptom.toString());
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
        try{
            String recordTime = pathParameters.get("recordTime");
            Symptom currSymptom = new Symptom().get(this.userId, "SYMPTOM#" + recordTime);

            if(currSymptom == null){
                constructResponse(404, null, "Symptom Not Found");
            }else{
                Gson gson = new Gson();
                Symptom newSymptom = gson.fromJson(this.body, Symptom.class);
                newSymptom.setUserId(this.userId);
                newSymptom.setRecordTime("SYMPTOM#" + recordTime);

                Symptom returnedSymptom = newSymptom.update();

                if(returnedSymptom != null){
                    responseData.put("symptom", returnedSymptom);
                    constructResponse(200, "Update Symptom Successfully", null);
                }else   constructResponse(401, null, "Update Symptom Failed");
            }


        }catch (Exception ex){
            LOG.error("error: {}", ex);
        }
    }

    @Override
    protected void handlePatchRequest(){

    }

    @Override
    protected void handleDeleteRequest(){
        try{
            String recordTime = pathParameters.get("recordTime");
            Boolean deleted = new Symptom().delete(this.userId, "SYMPTOM#" + recordTime);

            if(!deleted){
                constructResponse(404, null, "Symptom Not Found");
            }else{
                responseData.put("recordTime", recordTime);
                constructResponse(200, "Delete Symptom Successfully", null);
               
            }


        }catch (Exception ex){
            LOG.error("error: {}", ex);
        }
    }
}