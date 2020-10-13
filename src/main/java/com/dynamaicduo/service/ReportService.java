package com.dynamicduo.service;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public class ReportService extends Service{

    public ReportService(String userId, String path, String httpMethod, Map<String,String> pathParameters, Map<String,String> queryStringParameters, JsonNode body){
        super(userId, path, httpMethod, pathParameters, queryStringParameters, body);
    }

    @Override
    public void parseRequestSpecification(){

    };

    @Override
    public Object handleRequest() {
        LOG.info("handle in service: {}", "reports");
        return null;
    }
}