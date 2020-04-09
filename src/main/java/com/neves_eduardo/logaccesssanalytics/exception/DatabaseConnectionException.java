package com.neves_eduardo.logaccesssanalytics.exception;

public class DatabaseConnectionException extends RuntimeException{
    public DatabaseConnectionException(String msg) {
        super(msg);
    }
}
