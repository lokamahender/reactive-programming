package com.reactive.example.router;

import com.reactive.example.dao.CustomerImpl;
import com.reactive.example.dto.Employees;
import com.reactive.example.dto.EmployeesRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeesHandler {

    @Autowired
    private CustomerImpl customer;

    public Mono<ServerResponse> getEmployeeById(ServerRequest request){
       String response = customer.getCustomer(request.pathVariable("id"));
      return   ServerResponse
              .ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(Mono.just(response), Employees.class);
    }

    public Mono<ServerResponse> getEmployees(ServerRequest request){

        Mono<EmployeesRequest> requestMono = request.bodyToMono(EmployeesRequest.class);
        Mono<String> test1 = requestMono.map(t1->t1.getId1());
        Mono<String> test2 = requestMono.map(t1->t1.getId2());
        String response = customer.getCustomers(test1, test2);
        return   ServerResponse
                .ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(Mono.just(response), Employees.class);
    }
}
