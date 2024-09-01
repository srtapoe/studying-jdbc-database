package br.com.jdbc.studies.domain.exception;

public class BusinessRulesException extends RuntimeException{

    public BusinessRulesException(String message) {
        super(message);
    }
}
