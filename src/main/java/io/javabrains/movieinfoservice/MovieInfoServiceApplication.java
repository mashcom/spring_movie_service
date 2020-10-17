package io.javabrains.movieinfoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MovieInfoServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(MovieInfoServiceApplication.class, args);
    }


}
