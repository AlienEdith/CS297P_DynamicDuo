package com.dynamicduo.service;

import com.dynamicduo.database.Patient;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public class PatientService extends Service{

    public PatientService(String userId, String path, String httpMethod, Map<String,String> pathParameters, Map<String,String> queryStringParameters, JsonNode body){
        super(userId, path, httpMethod, pathParameters, queryStringParameters, body);
    }

    @Override
    public void parseRequestSpecification(){

    };

    @Override
    public Object handleRequest(){
        LOG.info("handle in service: {}", "patient");
        Object result = null;
        try{
            switch(this.httpMethod){
                case "GET":
                    Patient patient = new Patient().get(this.userId);
                    result = patient;
                    break;
                case "POST":
                    break;
                case "DELETE":
                    break;
                case "PUT":
                    break;
                default:
                    break;
            }
        }catch(Exception ex){
            LOG.error("Error " + ex);
        }
        
        return result;
    }

    
}