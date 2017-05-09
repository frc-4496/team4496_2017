package org.usfirst.frc.team4496.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//All the buttons and axes listed are for the Xbox controller
	public static Joystick controller = new Joystick(0);
	public static Button buttonA = new JoystickButton(controller, 1),
			buttonB = new JoystickButton(controller, 2),
			buttonX = new JoystickButton(controller, 3),
			buttonY= new JoystickButton(controller, 4),
			buttonLeftBumper = new JoystickButton(controller, 5),
			buttonRightBumper = new JoystickButton(controller, 6),
			buttonSelect = new JoystickButton(controller, 7),
			buttonStart = new JoystickButton(controller, 8),
			buttonLeftJoystick = new JoystickButton(controller, 9),
			buttonRightJoystick = new JoystickButton(controller, 10);
	/*
	 * For the joystick:
	 * rawAxis(0) = X Axis
	 * rawAxis(1) = Y Axis
	 * 			X and Y are the standard coordinate system on the controller
	 * 			X is side-to-side
	 * 			Y is forward-back
	 * rawAxis(2) = Z Axis (the rotating lever on the base
	 * 
	 * rawButton(1) = trigger
	 * Buttons 2-11 are listed on the controller 
	 */
	
}

