package com.oberasoftware.robo.dynamixel.handlers;

import com.oberasoftware.base.event.EventHandler;
import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.robo.dynamixel.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.oberasoftware.robo.core.ConverterUtil.intTo16BitByte;
import static com.oberasoftware.robo.core.ConverterUtil.toSafeInt;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class DynamixelServoModeHandler implements EventHandler {
    private static final Logger LOG = getLogger(DynamixelServoModeHandler.class);

    @Autowired
    private DynamixelConnector connector;

    @EventSubscribe
    public void receive(DynamixelServoMode command) {
        int servoId = toSafeInt(command.getServoId());
        LOG.debug("Received a servo mode: {}", command.getServoId(), command.getServoMode());

        DynamixelCommandPacket packet = new DynamixelCommandPacket(DynamixelInstruction.WRITE_DATA, servoId);
        if(command.getServoMode() == DynamixelServoMode.MODE.WHEEL_MODE) {
            packet.addParam(DynamixelAddress.CCW_ANGLE_LIMIT_L, intTo16BitByte(0));
        } else {
            packet.addParam(DynamixelAddress.CCW_ANGLE_LIMIT_L, intTo16BitByte(1023));
        }
        byte[] data = packet.build();
        byte[] received = connector.sendAndReceive(data);
        LOG.info("Package has been delivered");

        DynamixelReturnPacket returnPacket = new DynamixelReturnPacket(received);
        if (!returnPacket.hasErrors()) {
            LOG.debug("Mode for servo: {} set to: {}", servoId, command.getServoMode());
        } else {
            LOG.error("Could not set servo: {} to mode: {} reason: {}", servoId, command.getServoMode(), returnPacket.getErrorReason());
        }
    }
}
