package com.xpand.challenge.validator;

public interface Validator<T> {

    void validate(T toValidate);
    
}
