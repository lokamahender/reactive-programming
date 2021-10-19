package com.reactive.example.dao;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class CustomerImpl implements CustomerDao {

    private static final Logger logger = LoggerFactory.getLogger(CustomerImpl.class);

    @Autowired
    @Qualifier("oracleJdbcTemplate")
    private JdbcTemplate oracleJdbcTemplate;

    public String getCustomer(String id) {
        logger.info("--- customerId --- "+id);
        JSONObject resultObj = new JSONObject();
        try{
            String query = "select * from customers where customer_id = "+id;
            Map resultSet = oracleJdbcTemplate.queryForMap(query);
            if(resultSet != null){
                resultObj.put("customerId",resultSet.get("CUSTOMER_ID"));
                resultObj.put("customerName",resultSet.get("CUSTOMER_NAME"));
                resultObj.put("address",resultSet.get("ADDRESS"));
                resultObj.put("city",resultSet.get("CITY"));
                resultObj.put("phoneNumber",resultSet.get("PHONE_NUMBER"));
                resultObj.put("social",resultSet.get("SSN"));
            }
            logger.info("--- customer details ---\n"+resultObj);
            resultObj.put("statusCode",0);
            resultObj.put("statusMessage","records found in the db");
        }catch (Exception e){
            resultObj.put("statusCode",-1);
            resultObj.put("statusMessage",e.getMessage());
        }
        return resultObj.toString();
    }

    public String getCustomers(Mono<String> id1, Mono<String> id2) {
        //logger.info("--- customerId --- "+Mono.just(id1).flatMap() +","+Mono.just(id2));
        JSONObject resultObj = new JSONObject();
        JSONArray result = new JSONArray();
        try{
            String query = "select * from customers where customer_id in ("+Mono.just(id1)+","+Mono.just(id2)+")";
            SqlRowSet resultSet = oracleJdbcTemplate.queryForRowSet(query);
            while(resultSet.next()){
                resultObj.put("customerId",resultSet.getString("CUSTOMER_ID"));
                resultObj.put("customerName",resultSet.getString("CUSTOMER_NAME"));
                resultObj.put("address",resultSet.getString("ADDRESS"));
                resultObj.put("city",resultSet.getString("CITY"));
                resultObj.put("phoneNumber",resultSet.getString("PHONE_NUMBER"));
                resultObj.put("social",resultSet.getString("SSN"));
                result.put(resultObj);
            }
            logger.info("--- customer details ---\n"+ result);
            resultObj.put("statusCode",0);
            resultObj.put("statusMessage","records found in the db");
            result.put(resultObj);
        }catch (Exception e){
            resultObj.put("statusCode",-1);
            resultObj.put("statusMessage",e.getMessage());
            result.put(resultObj);
        }
        return result.toString();
    }
}