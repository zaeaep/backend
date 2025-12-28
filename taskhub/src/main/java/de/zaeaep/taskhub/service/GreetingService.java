package de.zaeaep.taskhub.service;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {
    public String greeting() {
        return "Hello Taskhub";
    }
}
