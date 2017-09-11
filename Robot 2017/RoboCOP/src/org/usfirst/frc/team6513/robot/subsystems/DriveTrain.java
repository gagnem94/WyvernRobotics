package org.usfirst.frc.team6513.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;

/**
 *
 */
public class DriveTrain extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private Spark frontLeftMotor;
	private Spark frontRightMotor;
	private Spark rearLeftMotor;
	private Spark rearRightMotor;
	private RobotDrive drive;
	
	public DriveTrain(){
		frontLeftMotor = new Spark(2);
		rearLeftMotor = new Spark(3);
		frontRightMotor = new Spark(0);
		rearRightMotor = new Spark(1);
		drive = new RobotDrive(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void arcadeDrive(Joystick stick) {
    	drive.arcadeDrive(stick);
    }
    
    public void autoStraightDrive(double moveValue, double rotateValue) {
    	drive.arcadeDrive(moveValue, rotateValue);
    }
    
    public void autoDrive(double leftPower, double rightPower){
    	frontLeftMotor.set(leftPower);
    	rearLeftMotor.set(leftPower);
    	frontRightMotor.set(rightPower);
    	rearRightMotor.set(rightPower);
    }
}

