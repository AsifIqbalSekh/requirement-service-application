package com.asifiqbalsekh.RequirementServiceBackend.utility;

import com.asifiqbalsekh.RequirementServiceBackend.DTO.GlobalErrorResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@ControllerAdvice
public class GlobalExceptionHandeller {

    private final Logger logger= LoggerFactory.getLogger(GlobalExceptionHandeller.class);

    @ExceptionHandler
    public ResponseEntity<GlobalErrorResponseDTO> handleAllExceptions(Exception e) {
        logger.error(e.getMessage());
        GlobalErrorResponseDTO response = new GlobalErrorResponseDTO();
        response.setMessage(e.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        LocalDateTime systemTime = LocalDateTime.now();
        response.setTimestamp(systemTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy 'Time' HH:mm:ss")));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }
}
