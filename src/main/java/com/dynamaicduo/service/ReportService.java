package com.dynamicduo.service;

import com.dynamicduo.database.Report;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.time.Instant;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;

public class ReportService extends Service{

    public ReportService(String userId, String path, String httpMethod, Map<String,String> pathParameters, Map<String,String> queryStringParameters, String body){
        super(userId, path, httpMethod, pathParameters, queryStringParameters, body);
    }

    private void getOneReport(){
        try{
            String recordTime = pathParameters.get("recordTime");
            Report report = new Report().get(this.userId, "REPORT#"+recordTime);
            if(report != null){
                responseData.put("report", report);
                constructResponse(200, "Get Report Successfully", null);
            }else   constructResponse(404, null, "Report Not Found");
        }catch (Exception ex){
            LOG.error("error: {}", ex);
        }
    }

    private void getAllReport(){
        try{
            List<Report> reports = new Report().list(this.userId);
            if(reports != null){
                responseData.put("reports", reports);
                constructResponse(200, "Get Reports Successfully", null);
            }else   constructResponse(401, null, "Get Reports Failed");
        }catch (Exception ex){
            LOG.error("error: {}", ex);
        }

    }

    @Override
    protected void handleGetRequest(){
        if(pathParameters != null && pathParameters.containsKey("recordTime")){
            // Get One Report
            getOneReport();
        }else{
            // Get All Reports
            getAllReport();
        }
    }

    @Override
    protected void handlePostRequest(){
        try{

            Gson gson = new Gson();
            Report report = gson.fromJson(this.body, Report.class);

            report.setUserId(this.userId);
            Long time = Instant.now().toEpochMilli();   // UTC time epoch in ms
            report.setRecordTime("REPORT#"+String.valueOf(time));

            Report returnedReport = report.save();

            if(returnedReport != null){
                responseData.put("report", returnedReport);
                constructResponse(200, "Create Report Successfully", null);
            }else   constructResponse(401, null, "Create Report Failed");

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
            String recordTime = pathParameters.get("recordTime");
            Report report  = new Report();
            Boolean result = report.delete(this.userId, "REPORT#"+recordTime);
            if(result){
                this.responseData.put("userId", this.userId);
                this.responseData.put("recordTime", "REPORT#"+recordTime);
                constructResponse(200, "Delete Report Successfully", null);
            }else   constructResponse(404, null, "Report Not Found");

        }catch (Exception ex){
            LOG.error("error: {}", ex);
        }

    }
}