package com.manoelalmeidaio.rinhadebackendjavaspring.config;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class RuntimeHintsConfiguration implements RuntimeHintsRegistrar {

  @Override
  public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
    hints.reflection().registerType(UUID[].class);
  }
}
