package com.example.sylphie.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:app.properties")
@ConfigurationProperties(prefix = "discord")
@Data
public class AppProperties {

    private String prefix;

    private String token;

}
