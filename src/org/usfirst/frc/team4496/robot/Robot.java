package org.usfirst.frc.team4496.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

/*
 * Part of the original robot code has been commented out because we were contemplating attending the 4th quarter assembly.
 * The code that you see not in comments has been translated from David Boehmer's Xbox controller to the black joystick that the program owns. 
 */
public class Robot extends IterativeRobot {
	public static OI oi;
	public static RobotMap robotMap;
	RobotDrive mainDrive;
	TalonSRX launchDrive, lift;
	Command autoMode;
	Servo stopper;
	Victor annoy;
	Timer tim;
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		mainDrive = new RobotDrive(0, 1, 2, 3);
		mainDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
		mainDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
		launchDrive = new TalonSRX(4);
		stopper = new Servo(5);
		lift = new TalonSRX(6);
		annoy = new Victor(7);
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
		CameraServer.getInstance().startAutomaticCapture();
	}
	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
public void disabledInit(){

}

public void disabledPeriodic() {
	Scheduler.getInstance().run();
}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		tim = new Timer();
		tim.start();				//starts a timer for autonomous movement
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		switch (autoSelected) {
		case customAuto:
			// Put custom auto code here
			
			//Moves forward for 3.25 seconds
			if(tim.get() <= 3.25) {
				mainDrive.mecanumDrive_Cartesian(0, -.25, 0, 0); //mainDrive.mecanumDrive_Cartesian(x, y, rotation, gyroAngle); moves robot with mecanum wheels	
			}
			
			//Turns counterclockwise
			else if(tim.get() <= 4.5){
				mainDrive.mecanumDrive_Cartesian(0, 0, -.25, 0);				
			}
			else if(tim.get() <= 6.0){
				mainDrive.mecanumDrive_Cartesian(0, -.25, 0, 0);
			}
			
			//Stops moving
			else
				mainDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
			
			break;
		case defaultAuto:
		default:
			// Put default auto code here

			//Moves forward for 3.5 seconds
			if(tim.get() <= 3.5) {
				mainDrive.mecanumDrive_Cartesian(0, -.25, 0, 0);	
			}
			else
				mainDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
			break;
		}
	}
	
	//I have no idea what this does
	
	/*
	 * import edu.wpi.first.wpilibj.Joystick;
	 * import edu.wpi.first.wpilibj.RobotDrive;
	 * import edu.wpi.first.wpilibj.IterativeRobot;
	 * 
	 * public class MecanumDefaultCode extends IterativeRobot{
	 * RobotDrive m_robotDrive = new RobotDrive(0, 1, 2, 3);
	 * Joystick m_DriveStick = new Joystick(1);
	 * 
	 * public void teleopPeriodic(){
	 * m_robotDrive.mecanumDrive_Cartesian(m_driveStick.getX(), m_driveStick.getY(), m_driveStick.getTwist(), 0);
	 * }
	 * }
	 */

	/**
	 * This function is called periodically during operator control
	 */
	@SuppressWarnings("deprecation")
	@Override
	
	//Most of the comments with values in this section have been commented out because of the assembly
	
	public void teleopPeriodic() {

		//Main drive setup
    		
        //Getting and rounding the input values
        double lXVal = OI.controller.getRawAxis(0);
        double lYVal = OI.controller.getRawAxis(1);        
        //double rXVal = OI.controller.getRawAxis(4);
        //double rYVal = OI.controller.getRawAxis(5);
        double turn = OI.controller.getRawAxis(2);
        
        /*
         * sideDrv = mecanum side-to-side
         * fwdDrv = forward and backwards
         * sldDrv = turning
         */
        double sideDrv, fwdDrv, sldDrv;
        if(Math.abs(lXVal) > 0.2)
        	sideDrv = lXVal / 2;
        else
        	sideDrv = 0;
<<<<<<< HEAD
        
        //this commented portion allows for double-joystick forward drive
        
        /*if(Math.abs(lYVal) > 0.2 || Math.abs(rYVal) > 0.2){
        	if(Math.abs(lYVal) > 0.2){
        		fwdDrv = lYVal;
        	}
        	else
        		fwdDrv = rYVal;
        }
        */
        if(Math.abs(lYVal) > 0.2){        	
        	fwdDrv = lYVal / 2;
        }
=======
        if(Math.abs(lYVal) > 0.2 || Math.abs(rYVal) > 0.2){
        	if(Math.abs(lYVal) > 0.2)
        		fwdDrv = lYVal;
        	else
        		fwdDrv = rYVal;
        }
>>>>>>> origin/master
        else
        	fwdDrv = 0;
        if(Math.abs(turn) > 0.2)
        	sldDrv = turn / 2;
        else
        	sldDrv = 0;
        mainDrive.mecanumDrive_Cartesian(sideDrv, fwdDrv, sldDrv, 0);
        
        
        //This commented portion was initially set to activate the ball launcher
        
        /*if(OI.controller.getRawAxis(3) !=0){
        	launchDrive.set(1);
        }
        else
        	launchDrive.set(0);
         */
        
        if(OI.controller.getRawButton(1)){
        	launchDrive.set(1);
        }
        
        if(OI.controller.getRawButton(2))
        	launchDrive.set(0);
        
        /*
         * Servo activation
         * SmartDashboard is the viewing window that opens with the driver station
         */
        if(OI.controller.getRawButton(5)){ //change to 2 when done with assembly
        	stopper.set(1);
        	SmartDashboard.putString("Servo Status", "Closed");
        }
        else if (OI.controller.getRawButton(4)){ //change to 1 when done with assembly
        	stopper.set(0.7);
            SmartDashboard.putString("Servo Status", "Open");
        }
        
        //The following comment is the initial code for the agitator
        
        /*if(OI.controller.getRawAxis(2) !=0)
        	annoy.set(.5);
        else {
        	annoy.set(0);
        }
       */
        if(OI.controller.getRawButton(7)){
        	annoy.set(0.5);
        }
        if(OI.controller.getRawButton(10)){
        	annoy.set(0);
        }
        
        //The following comment is the code for climbing the rope
        
        /* if(OI.controller.getRawButton(8) && OI.controller.getRawButton(9)){//change to 3 and 6, respectively, when done with assembly
        	lift.set(1);
        }
        /*else if (OI.controller.getRawButton(4)){
        	lift.set(-1);
        }				//not a part of original code
        else if (OI.controller.getRawButton(7)){ //change to 4 when done with assembly
        	lift.set(0);        	
        }
        else if (OI.controller.getRawButton(6) && OI.controller.getRawButton(7)){ //change to 4 and 6, respectively, when done with assembly
        	lift.set(0.5);
        }*/
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}
