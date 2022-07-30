package org.based;

import org.based.input.HostPathProperties;
import org.based.input.InputConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(HostPathProperties.class)
public class TodoClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodoClientApplication.class);
    }
}
