package org.usfirst.frc.team4496.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.TalonSRX;
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
public class Robot extends IterativeRobot {
	public static OI oi;
	public static RobotMap robotMap;
	RobotDrive mainDrive;
	TalonSRX launchDrive, lift;
	Command autoMode;
	Servo stopper;
	Victor annoy;
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
		annoy = new Victor(6);
		lift = new TalonSRX(7);
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
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
			break;
		case defaultAuto:
		default:
			// Put default auto code here
			break;
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
//Main drive setup
        
        //Getting and rounding the input values
        double lXVal = OI.controller.getRawAxis(0);
        double lYVal = OI.controller.getRawAxis(1);
        double rXVal = OI.controller.getRawAxis(4);
        
        /*//Slowing the drive by the triggers
        double trigger = 20;
        
        //Round and process the input 
        double rotDrv = ((double)((int)(lXVal  * 10)) ) / trigger;
        double fwdDrv = ((double)((int)(lYVal * 10)) ) / trigger;
        double sldDrv = ((double)((int)(rXVal  * 10)) ) / trigger;
        */
        double rotDrv, fwdDrv, sldDrv;
        if(Math.abs(lXVal) > 0.05)
        	rotDrv = lXVal / 2;
        else
        	rotDrv = 0;
        if(Math.abs(lYVal) > 0.05)
        	fwdDrv = lYVal / 2;
        else
        	fwdDrv = 0;
        if(Math.abs(rXVal) > 0.05)
        	sldDrv = rXVal / 2;
        else
        	sldDrv = 0;
        mainDrive.mecanumDrive_Cartesian(rotDrv, fwdDrv, sldDrv, 0);
        
        if(OI.controller.getRawAxis(3) !=0){
        	launchDrive.set(1);
        }
        else
        	launchDrive.set(0);

        if(OI.controller.getRawButton(1)){
        	stopper.set(1);
        	SmartDashboard.putString("Servo Status", "Closed");
        }
        else if (OI.controller.getRawButton(2)){
        	stopper.set(0.7);
            SmartDashboard.putString("Servo Status", "Open");
        }
         if(OI.controller.getPOV() == 180){
        	lift.set(-.5);
        } else if(OI.controller.getPOV() == 0) {
        	lift.set(.75);
        } else {
        	lift.set(0);
        }
       
        if(OI.controller.getRawButton(3)){
        	annoy.set(1);
        }
        else if (OI.controller.getRawButton(4)){
        	annoy.set(-1);
        }
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

