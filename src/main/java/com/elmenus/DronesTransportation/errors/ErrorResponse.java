package com.elmenus.DronesTransportation.errors;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private Boolean success;
    private String message;
    private HttpStatus status;
    private LocalDateTime dateTime;
    private List<String> details;

    public ErrorResponse(String message, HttpStatus status, List<String> errors){
        super();
        this.dateTime = LocalDateTime.now();
        this.success = Boolean.FALSE;
        this.message = message;
        this.status = status;
        this.details = errors;
    }
}
