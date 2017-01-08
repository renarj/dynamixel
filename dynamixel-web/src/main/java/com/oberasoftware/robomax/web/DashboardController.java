package com.oberasoftware.robomax.web;

import com.oberasoftware.robo.api.Robot;
import com.oberasoftware.robo.api.RobotRegistry;
import com.oberasoftware.robo.api.servo.Servo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Renze de Vries
 */
@Controller
public class DashboardController {
    private static final Logger LOG = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private RobotRegistry robotRegistry;

    @RequestMapping("/")
    public String getIndex(Model model) {
        LOG.info("Index was requested");
        List<Robot> robots = robotRegistry.getRobots();

        List<String> motionIds = robots.stream().map(r -> r.getMotionEngine().getMotions()).flatMap(Collection::stream)
                .collect(Collectors.toList());
        List<Servo> servos = robots.stream().map(r -> r.getServoDriver().getServos())
                .flatMap(Collection::stream).collect(Collectors.toList());
        model.addAttribute("motions", motionIds);
        model.addAttribute("servos", servos.stream()
                .map(SimpleServo::new)
                .collect(Collectors.toList()));

        return "index";
    }
}
