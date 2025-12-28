package de.zaeaep.taskhub.controller;

import de.zaeaep.taskhub.service.GreetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HelloController {
    private final GreetingService greetingService;

    @GetMapping("/api/hello")
    public HelloResponse hello() {
        return new HelloResponse(greetingService.greeting());
    }

    public record HelloResponse(String message) {}

}
