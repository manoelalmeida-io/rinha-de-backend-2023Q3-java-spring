package com.manoelalmeidaio.rinhadebackendjavaspring;

import com.manoelalmeidaio.rinhadebackendjavaspring.config.RuntimeHintsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(RuntimeHintsConfiguration.class)
public class RinhaDeBackendJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RinhaDeBackendJavaApplication.class, args);
	}

}
