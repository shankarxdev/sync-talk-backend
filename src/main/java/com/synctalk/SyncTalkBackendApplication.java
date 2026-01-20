package com.synctalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SyncTalkBackendApplication {

    static void main(String[] args) {
        SpringApplication.run(SyncTalkBackendApplication.class, args);
    }

}
