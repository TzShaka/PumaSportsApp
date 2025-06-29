package com.example.ms.proveedor2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//@EnableJpaRepositories

@SpringBootApplication
//@EnableEurekaClient
public class MsProveedor2Application {

    public static void main(String[] args) {
        SpringApplication.run(MsProveedor2Application.class, args);
    }

}
