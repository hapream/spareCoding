package com.hapream.demo;

public class EchoServiceImpl implements EchoService {
    @Override
    public String echo(String message) {
        return message;
    }
}
