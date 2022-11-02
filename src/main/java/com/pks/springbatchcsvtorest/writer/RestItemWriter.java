package com.pks.springbatchcsvtorest.writer;

import com.google.gson.Gson;
import com.pks.springbatchcsvtorest.domain.MyRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Slf4j
public class RestItemWriter<Person> implements ItemWriter<Person> {

    private static final Gson GSON = new Gson();

    public URI uri = URI.create("http://localhost:8080/person");

    @Autowired
    RestTemplate restTemplate;

    @Override
    public void write(List<? extends Person> list) throws Exception {
        MyRequest request = new MyRequest();
        request.setRequestName("sample");
        request.setRequestId("myReqId");
        request.setPersonList((List<com.pks.springbatchcsvtorest.domain.Person>) list);

        String jsonRequest = GSON.toJson(request);
        log.info("jsonRequest: " + jsonRequest);

        HttpHeaders httpHeaders = getHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonRequest, httpHeaders);
        ResponseEntity<String> responseEntity = null;
        restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

    }

    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediaTypes);
        return httpHeaders;
    }

    private String getNewRequestId() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        log.info("Generated RequestId: " + generatedString);
        return generatedString;
    }
}
