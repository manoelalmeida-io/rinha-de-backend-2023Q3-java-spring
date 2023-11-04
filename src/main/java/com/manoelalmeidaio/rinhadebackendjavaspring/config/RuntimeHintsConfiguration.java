package com.manoelalmeidaio.rinhadebackendjavaspring.config;

import com.manoelalmeidaio.rinhadebackendjavaspring.core.pessoa.Pessoa;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.UUID;

@Configuration
public class RuntimeHintsConfiguration implements RuntimeHintsRegistrar {

  @Override
  public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
    hints.serialization().registerType(Pessoa.class).registerType(UUID.class).registerType(LocalDate.class);
    hints.reflection().registerType(UUID[].class);
  }
}
