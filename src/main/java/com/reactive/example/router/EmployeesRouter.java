package com.reactive.example.router;

import com.reactive.example.dto.Employees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
public class EmployeesRouter {

    @Autowired
    EmployeesHandler employeesHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(){
       return RouterFunctions.route()
                .GET("/getEmployeeById/{id}", employeesHandler::getEmployeeById)
                .POST("/getEmployees", employeesHandler::getEmployees)
                .build();
    }
}
