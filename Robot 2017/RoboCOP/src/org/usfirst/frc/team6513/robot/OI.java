package org.usfirst.frc.team6513.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

public class OI {
	Joystick stick = new Joystick(0);
	Button fullspeedClimb = new JoystickButton(stick, 1);
	
	
	
public OI() {

	fullspeedClimb.whileHeld(new climb());
}

}
