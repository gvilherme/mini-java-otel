package br.com.gtechnologia.mini_java_otel.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.gtechnologia.mini_java_otel")
public class MiniJavaOtelApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniJavaOtelApplication.class, args);
	}

}
