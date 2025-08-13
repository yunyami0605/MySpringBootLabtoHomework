package com.homework1.MySpringbootLab;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "myprop")
public class MyPropProperties {

    private String username;
    private int port;
}
