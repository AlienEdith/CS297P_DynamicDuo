package com.dynamicduo.service;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public class ReportService extends Service{

    public ReportService(String userId, String path, String httpMethod, Map<String,String> pathParameters, Map<String,String> queryStringParameters, String body){
        super(userId, path, httpMethod, pathParameters, queryStringParameters, body);
    }

    @Override
    protected void handleGetRequest(){
        LOG.info("handle in service: {}", "report");
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