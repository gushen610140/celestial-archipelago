package icu.sunway.cagateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CaGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaGatewayApplication.class, args);
    }
}
