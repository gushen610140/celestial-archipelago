package icu.sunway.cadawn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
public class CaDawnApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaDawnApplication.class, args);
	}

	@RestController
	class EchoController {
		@GetMapping("/echow/{string}")
		public String echo(@PathVariable String string) {
			return string;
		}
	}
}
