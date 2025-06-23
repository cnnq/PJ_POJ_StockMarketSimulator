package com.stockmarket.exception;

public class InvalidCsvException extends Exception {
    public InvalidCsvException(String line) {
        super("Invalid CSV file format in line: " + line);
    }
}
