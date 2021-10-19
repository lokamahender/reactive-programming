package com.reactive.example.dao;

import org.json.JSONObject;
import reactor.core.publisher.Mono;

public interface CustomerDao {

    public String getCustomer(String id);

    public String getCustomers(Mono<String> id1, Mono<String> id2);

}