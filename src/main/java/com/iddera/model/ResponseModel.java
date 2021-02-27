package com.iddera.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@AllArgsConstructor
@Data
public class ResponseModel {
    private Object data;
    private String message;
    private List<String> errors;
    private int status;

    public ResponseModel(Object data) {
        this.data = data;
        this.status = HttpStatus.OK.value();
        this.message = "Request successful";
    }
}
