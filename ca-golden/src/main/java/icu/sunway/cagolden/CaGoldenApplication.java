package icu.sunway.cagolden;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
public class CaGoldenApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaGoldenApplication.class, args);
	}

	@RestController
	class EchoController {
		@GetMapping("/echo/{string}")
		public String echo(@PathVariable String string) {
			return string;
		}
	}
}
