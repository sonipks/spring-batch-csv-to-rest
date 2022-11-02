package com.pks.springbatchcsvtorest.domain;

import lombok.Data;

import java.util.List;

@Data
public class MyRequest {

    private String requestName;
    private String requestId;
    private List<Person> personList;
}
