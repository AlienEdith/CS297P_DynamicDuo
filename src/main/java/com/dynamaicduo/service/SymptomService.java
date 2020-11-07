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
            String recordTime = pathParameters.get("recordTime");
            LOG.info("recordTime: {}" + recordTime);
            try{
                Symptom symptom = new Symptom().getOneSymptom(userId, "SYMPTOM#" + recordTime);
                if(symptom != null){
                    responseData.put("symptom", symptom);
                    constructResponse(200, "Get Symptom Successfully", null);
                }else   constructResponse(401, null, "Get Symptom Failed");
            }catch(Exception ex) {
                LOG.error("error: {}" + ex);
            }
        }else{
            // Get Symptoms: condition and other requirements as query strings 
            String condition = pathParameters.getOrDefault("condition", "");
            LOG.info("condtion:{}" + condition);
            switch(condition){
                case "":
                    // Get All Symptoms
                    String lastDays = queryStringParameters.getOrDefault("lastDays","");
                    String resolvedState = queryStringParameters.getOrDefault("resolvedState","");
                    String sortBy = queryStringParameters.getOrDefault("sortBy","");
                    try {
                        Symptom symptom = new Symptom();
                        Symptom[] symptoms = symptom.getSymptoms(userId, lastDays, resolvedState, sortBy);
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
                    String startTime = queryStringParameters.getOrDefault("startTime", "0");
                    String endTime = queryStringParameters.getOrDefault("endTime", String.valueOf(new Date().getTime()));
                    LOG.info("startTime:{}" + startTime);
                    try {
                        Symptom symptom = new Symptom();
                        Symptom[] symptoms = symptom.getSymptomsByCompletionRange(userId, startTime, endTime);
                        if(symptoms != null){
                            responseData.put("symptoms", symptoms);
                            constructResponse(200, "Get Symptoms Successfully", null);
                        }else   constructResponse(401, null, "Get Symptoms Failed");
                    }catch(Exception ex) {
                        LOG.error("error: {}", ex);
                    }
                    break;
                case "review":
                    // Get Review Symptoms
                    startTime = queryStringParameters.getOrDefault("startTime", "0");
                    endTime = queryStringParameters.getOrDefault("endTime", String.valueOf(new Date().getTime()));
                    LOG.info("startTime:{}" + startTime);
                    try {
                        Symptom symptom = new Symptom();
                        Symptom[] symptoms = symptom.getReviewSymptoms(userId, startTime, endTime);
                        if(symptoms != null){
                            responseData.put("symptoms", symptoms);
                            constructResponse(200, "Get Symptoms Successfully", null);
                        }else   constructResponse(401, null, "Get Symptoms Failed");
                    }catch(Exception ex) {
                        LOG.error("error: {}", ex);
                    }
                    break;
                default:
                    break;
            }

        }
    }

    @Override
    protected void handlePostRequest(){
       
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
            symptom.setReviewTime("0");
            symptom.setResolvedDate("0");
            symptom.setBetterCondition("");
            symptom.setWorseCondition("");
            symptom.setImpactToLife("0");
            symptom.setOtherRelatedSymptom("");
            LOG.info("symptom: {}" + symptom.toString());

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
        handlePutRequest();
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