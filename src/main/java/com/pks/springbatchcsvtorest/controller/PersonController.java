package com.pks.springbatchcsvtorest.controller;

import com.pks.springbatchcsvtorest.domain.MyRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PersonController {

    private static int requestCounter;


    @PostMapping("/person")
    public ResponseEntity<String> addPerson(@RequestBody MyRequest request) {
        log.info("Received request[]: []", requestCounter, request);
        ResponseEntity<String> responseEntity = new ResponseEntity<>("MyResponse", HttpStatus.ACCEPTED);
        return responseEntity;
    }
}
