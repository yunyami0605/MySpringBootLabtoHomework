package com.homework1.MySpringbootLab;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Builder
public class MyEnvironment {
    String mode;

    public MyEnvironment(String mode) {
        this.mode = mode;
    }

}
