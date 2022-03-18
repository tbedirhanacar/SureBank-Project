package com.bank.sure.contoller.response;

import lombok.Data;

@Data //Creates getters setters constructors easily (Lombok)

public class Response {

    boolean isSuccess;
    String message;
}
