package io.github.joker.pper.multi.env.sample.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:sys-env.properties", "classpath:sys-env-${sys.env}.properties"})
public class ImportEnvConfig {
}
