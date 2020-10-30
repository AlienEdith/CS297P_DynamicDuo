package com.dynamicduo.service;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

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