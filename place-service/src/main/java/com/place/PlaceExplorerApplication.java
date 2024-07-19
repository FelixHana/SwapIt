package com.place;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class PlaceExplorerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlaceExplorerApplication.class, args);
    }

}
