package org.incode.estcodasubscriber.spi.rest.impl;

import org.incode.estcodasubscriber.spi.rest.RelayRestModule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringContextIntegrationTest.RelayRestTestApp.class)
public class SpringContextIntegrationTest {

    @SpringBootApplication
    @Import({RelayRestModule.class})
    static class RelayRestTestApp { }

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }

}
