package org.usfirst.frc.team6513.robot.commands;

import org.usfirst.frc.team6513.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutoCommand extends Command {
	public AutoCommand() {
		requires(Robot.exampleSubsystem);
	}
	
	protected void initialize() {
		
	}
	
	protected void execute() {
		
	}

	protected boolean isFinished() {	
		return false;
	}
	
	protected void end() {
		
	}
	
	protected void interrupted() {
		end();
	}
	

}
