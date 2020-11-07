package com.dynamicduo.service;

import com.dynamicduo.database.Patient;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;

public class PatientService extends Service{

    public PatientService(String userId, String path, String httpMethod, Map<String,String> pathParameters, Map<String,String> queryStringParameters, String body){
        super(userId, path, httpMethod, pathParameters, queryStringParameters, body);
    }

    @Override
    protected void handleGetRequest(){
        try{

            Patient patient = new Patient().get(this.userId);
            if(patient != null){
                responseData.put("patient", patient);
                constructResponse(200, "Get Patient Successfully", null);
            }else   constructResponse(404, null, "Patient Not Found");

        }catch (Exception ex){
            LOG.error("error: {}", ex);
        }

    }
    
    @Override
    protected void handlePostRequest(){
        try{

            Gson gson = new Gson();
            Patient patient = gson.fromJson(this.body, Patient.class);

            patient.setRecordTime("PATIENT");
            patient.setImage("");
            patient.setLatestFourSymptoms(new ArrayList<>());

            Patient returnedPatient = patient.save();
            LOG.info("returnedPatient: {}", returnedPatient);
            if(returnedPatient != null){
                responseData.put("patient", returnedPatient);
                constructResponse(200, "Create Patient Successfully", null);
            }else   constructResponse(401, null, "Create Patient Failed");

        }catch (Exception ex){
            LOG.error("error: {}", ex);
        }
    }

    @Override
    protected void handlePutRequest(){
        try{
            Patient currPatient = new Patient().get(this.userId);

            if(currPatient == null){
                constructResponse(404, null, "Patient Not Found");
            }else{
                Gson gson = new Gson();
                Patient newPatient = gson.fromJson(this.body, Patient.class);
                newPatient.setUserId(this.userId);
                newPatient.setRecordTime("PATIENT");

                Patient returnedPatient = newPatient.update();

                if(returnedPatient != null){
                    responseData.put("patient", returnedPatient);
                    constructResponse(200, "Update Patient Successfully", null);
                }else   constructResponse(401, null, "Update Patient Failed");
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

            Patient patient  = new Patient();
            Boolean result = patient.delete(this.userId);
            if(result){
                this.responseData.put("userId", this.userId);
                constructResponse(200, "Delete Patient Successfully", null);
            }else   constructResponse(404, null, "Patient Not Found");

        }catch (Exception ex){
            LOG.error("error: {}", ex);
        }

    }
}