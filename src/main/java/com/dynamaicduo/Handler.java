package com.dynamicduo;

import com.dynamicduo.service.Service;
import com.dynamicduo.service.PatientService;
import com.dynamicduo.service.SymptomService;
import com.dynamicduo.service.ReportService;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Handler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private static final Logger LOG = LogManager.getLogger(Handler.class);

	// private static final String USER = "users";
	// private static final String SYMPTOM = "symptoms";
	// private static final String REPORT = "reports";

	private Service service;

	private String getUserIdFromAuthorizer(Map<String, Object> input){
		// Map<String, Object> requestContext = (Map<String,Object>)input.get("requestContext");
		// Map<String, Object> authorizer = (Map<String,Object>)requestContext.get("authorizer");
		// Map<String, Object> claims = (Map<String,Object>)authorizer.get("claims");
		// String userId = (String)claims.get("sub");

		String userId = "1";

		return userId;
	}

	private String getService(String path){
		String[] pathArray = path.split("/");
		// for(String s : pathArray)	LOG.info("path: {}", s);
		return pathArray[1];
	}

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

		try{
			String path = (String)input.get("path");
			LOG.info("path: {}", path);
			String httpMethod = (String)input.get("httpMethod");
			LOG.info("httpMethod: {}", httpMethod);
			Map<String,String> pathParameters =  (Map<String,String>)input.get("pathParameters");
			LOG.info("pathParameters: {}", pathParameters);
			Map<String,String> queryStringParameters =  (Map<String,String>)input.get("queryStringParameters");
			LOG.info("queryStringParameters: {}", queryStringParameters);
			// JsonNode body = new ObjectMapper().readTree((String) input.get("body"));
			JsonNode body = null;
			String userId = getUserIdFromAuthorizer(input);
			// LOG.info("userId: {}", userId);

			// Parse the path 
			String serviceType = getService(path);
			switch(serviceType){
				case "users":
					LOG.info("user service - userId: {}", userId);
					service = new PatientService(userId, path, httpMethod, pathParameters, queryStringParameters, body);
					break;
				case "symptoms":
					service = new SymptomService(userId, path, httpMethod, pathParameters, queryStringParameters, body);
					break;				
				case "reports":
					service = new ReportService(userId, path, httpMethod, pathParameters, queryStringParameters, body);
					break;
				default:
					break;			
			}

			Object data = service.handleRequest();

			Map<String, Object> response = new HashMap<>();
			response.put("data", data);

			return ApiGatewayResponse.builder()
					.setStatusCode(200)
					.setObjectBody(response)
					.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
					.build();

		}catch (Exception ex) {
          LOG.error("Error " + ex);

          // send the error response back
    			Response responseBody = new Response("Error ", input);
    			return ApiGatewayResponse.builder()
    					.setStatusCode(500)
    					.setObjectBody(responseBody)
    					.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"))
    					.build();
      }
	}
}
