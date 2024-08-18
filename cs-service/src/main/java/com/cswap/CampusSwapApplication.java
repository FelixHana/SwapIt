package com.cswap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class CampusSwapApplication {
    public static void main(String[] args) {
        SpringApplication.run(CampusSwapApplication.class, args);
    }

}
