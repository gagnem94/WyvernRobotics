package org.usfirst.frc.team6513.robot;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Command;

public class climb extends Command {
	private Jaguar climbMotor;
	
	public climb() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.exampleSubsystem);
		climbMotor = new Jaguar(4);
		climbMotor.enableDeadbandElimination(true);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		climbMotor.set(-0.45);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
	
	protected void end() {
		
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		climbMotor.set(0.0);
	}

}
