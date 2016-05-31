This library can be used to interact directly with Dynamixel motors. This library has only been tested with AX-12A Dynamixel servo's.

 # Usage

Here is a small Spring boot based application that reads out the Servo's. In order to initialise the servo's there needs to be a spring application.properties file as following on the classpath:

File: application.properties
```
#Dynamixel port
dynamixel.serial.port=/dev/tty.usbmodem14141

```

The spring boot based application:
```
@SpringBootApplication
@Import(DynamixelConfiguration.class)
public class DynamixelTest {
    private static final Logger LOG = LoggerFactory.getLogger(DynamixelTest.class);

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DynamixelTest.class);
        ServoDriver servoDriver = context.getBean(ServoDriver.class);
        servoDriver.getServos().forEach(s -> {
            LOG.info("Servo found: {} on position: {}", s.getId(), s.getData().getValue(ServoProperty.POSITION));
        });
        //example position set and speed for setting it
        servoDriver.setPositionAndSpeed("1", 100, 180);
    }
}
```

There is nothing stopping you from using this in a non-spring-boot application this is just faster for demo purposes. Just ensure the DynamixelConfiguration bean gets loaded into your spring context.