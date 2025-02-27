package com.example.manyToManyDemo.exception;

public class ResourceNotFoundException extends RuntimeException{
public ResourceNotFoundException (String message) {
    super(message);
}
}
