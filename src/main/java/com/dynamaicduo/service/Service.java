package com.dynamicduo.service;

// import com.dynamicduo.utils.HttpMethod;

import java.util.Map;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;

abstract public class Service {
    protected String userId;
    protected String path;
    protected String httpMethod;
    protected Map<String,String> pathParameters;
    protected Map<String,String> queryStringParameters;
    protected JsonNode body;

    protected final Logger LOG = LogManager.getLogger(this.getClass());

    protected Map<String, Object> result;

    public Service(String userId, String path, String httpMethod, Map<String,String> pathParameters, Map<String,String> queryStringParameters, JsonNode body){
        this.userId = userId;
        this.path = path;
        this.httpMethod = httpMethod;
        this.pathParameters = pathParameters;
        this.queryStringParameters = queryStringParameters;
        this.body = body;
        this.result = new HashMap<>();
    }

    protected void constructResponse(int statusCode, String message, String error, Object data){
        this.result.put("statusCode", statusCode); 
        if(message != null) this.result.put("message", message);
        if(error != null) this.result.put("error", error);
        if(data != null) this.result.put("data", data);
    }

    protected void parseHttpMethod() {
        switch(this.httpMethod){
            case "GET":
                handleGetRequest();
                break;
            case "POST":
                handlePostRequest();
                break;
            case "PUT":
                handlePutRequest();
                break;
            case "PATCH":
                handlePatchRequest();
                break;
            case "DELETE":
                handleDeleteRequest();
                break;
            default:
                break;
        }
    }

    protected abstract void handleGetRequest();
    protected abstract void handlePostRequest();
    protected abstract void handlePutRequest();
    protected abstract void handlePatchRequest();
    protected abstract void handleDeleteRequest();
    
    public Map<String, Object> handleRequest(){
        parseHttpMethod();

        return result;
    };
}