package com.oberasoftware.robo.dynamixel;

import com.google.common.collect.ImmutableMap;
import com.oberasoftware.robo.api.servo.ServoDriver;
import com.oberasoftware.robo.api.servo.ServoProperty;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

/**
 * @author Renze de Vries
 */
@SpringBootApplication
@Import(DynamixelConfiguration.class)
@Ignore
public class DynamixelTest {
    private static final Logger LOG = LoggerFactory.getLogger(DynamixelTest.class);

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DynamixelTest.class);
        ServoDriver servoDriver = context.getBean(ServoDriver.class);
        servoDriver.activate(null, ImmutableMap.<String, String>builder().put(DynamixelServoDriver.PORT, "/dev/tty.usbmodem1431").build());
        servoDriver.getServos().forEach(s -> {
            LOG.info("Servo found: {} on position: {}", s.getId(), s.getData().getValue(ServoProperty.POSITION));
        });
    }
}
