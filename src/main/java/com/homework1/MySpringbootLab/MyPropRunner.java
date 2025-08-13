package com.homework1.MySpringbootLab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
public class MyPropRunner implements ApplicationRunner {
    // application.properties에서 값 로드
    @Value("${myprop.username}")
    private String username;

    @Value("${myprop.port}")
    private int port;

    @Autowired
    private MyEnvironment environment;

    @Autowired
    private MyPropProperties myPropProperties;

    private Logger logger = LoggerFactory.getLogger(MyPropRunner.class);

    public void printTo() {
//        System.out.println("username : " + username);
//        System.out.println("port : " + port);
        logger.debug("myboot.mode d = {}", environment.getMode());
        logger.info("myboot.mode = {}", environment.getMode());

        logger.info("Username: {}", myPropProperties.getUsername());
        logger.debug("Port: {}", myPropProperties.getPort());
        logger.info("Mode: {}", environment.getMode());
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //
        printTo();
    }
}
