package com.oberasoftware.robo.dynamixel;

import com.oberasoftware.robo.api.servo.ServoCommand;

/**
 * @author renarj
 */
public class DynamixelServoMode implements ServoCommand {
    public enum MODE {
        JOINT_MODE,
        WHEEL_MODE
    }

    private String servoId;
    private MODE servoMode;

    public DynamixelServoMode(String servoId, MODE servoMode) {
        this.servoId = servoId;
        this.servoMode = servoMode;
    }

    @Override
    public String getServoId() {
        return servoId;
    }

    public MODE getServoMode() {
        return servoMode;
    }

    @Override
    public String toString() {
        return "DynamixelServoMode{" +
                "servoId='" + servoId + '\'' +
                ", servoMode=" + servoMode +
                '}';
    }
}
