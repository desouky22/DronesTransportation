package com.elmenus.DronesTransportation.errors;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private Boolean success;
    private String message;
    private HttpStatus status;
    private LocalDateTime dateTime;

    public ErrorResponse(String message, HttpStatus status){
        super();
        this.dateTime = LocalDateTime.now();
        this.success = Boolean.FALSE;
        this.message = message;
        this.status = status;
    }
}
