package QuantityMeasurementApp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
  
    @GetMapping("/")
    public String home() {
        return "Spring Boot is running 🚀";
    }
}