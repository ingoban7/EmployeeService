package com.finicity.hr.emp.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private int statusCode;
    private String message;
    private String cause;

    public ErrorResponse(int statusCode, String message){
        this.statusCode = statusCode;
        this.message = message;
    }

}
