package com.jobportal.jobportal;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public String getWelcomeMessage() {
        return "Welcome to coding";
    }
}