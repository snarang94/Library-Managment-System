package com.library.demo;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.library.dbConnection.DatabaseConnection;

@Component
public class SpringDestroyOperations {

	@PreDestroy
    public void destroy() {
        DatabaseConnection databaseConnection = DatabaseConnection.getDatabaseConnectionInstance();
        databaseConnection.closeConnection();
    }
}
