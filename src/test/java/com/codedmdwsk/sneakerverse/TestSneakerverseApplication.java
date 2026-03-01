package com.codedmdwsk.sneakerverse;

import org.springframework.boot.SpringApplication;

public class TestSneakerverseApplication {

    public static void main(String[] args) {
        SpringApplication.from(SneakerverseApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
