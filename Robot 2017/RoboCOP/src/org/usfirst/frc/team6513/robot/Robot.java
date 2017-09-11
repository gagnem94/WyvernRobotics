
package org.usfirst.frc.team6513.robot;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI.Port;
//import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team6513.robot.commands.AutoCommand;
import org.usfirst.frc.team6513.robot.commands.ExampleCommand;
import org.usfirst.frc.team6513.robot.subsystems.DriveTrain;
import org.usfirst.frc.team6513.robot.subsystems.ExampleSubsystem;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	//RobotDrive myRobot = new RobotDrive(2, 3, 0, 1);
	Joystick stick = new Joystick(0);
	DriveTrain drive;
	Button climbButton = new JoystickButton(stick, 1);
	Timer timer = new Timer();
	ADXRS450_Gyro gyro = new ADXRS450_Gyro(Port.kOnboardCS0); // GYROCODE
	DoubleSolenoid piston = new DoubleSolenoid(0, 1); // PNEUMATICSCODE
	
	
	private Compressor compressor; // PNEUMATICS CODE
	private PowerDistributionPanel pdp;

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;
	
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		drive = new DriveTrain();
		oi = new OI();
		
		compressor = new Compressor(); // PNEUMATICS CODE
		pdp = new PowerDistributionPanel();
		compressor.start();
		
		// Adds auto commands to the Smart Dash to choose before a match
		chooser.addDefault("Default Auto", new ExampleCommand());
		chooser.addObject("My Auto", new AutoCommand());
		SmartDashboard.putData("Auto mode", chooser);
		
		// Initializes the USB camera and sets the resolution and FPS
		CameraServer vision = CameraServer.getInstance();
		VideoSource usbCam = vision.startAutomaticCapture("cam0", 0);
		usbCam.setResolution(640, 480);
		usbCam.setFPS(30);
		
		
		gyro.reset(); // GYROCODE
		
	}

	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();
		
		gyro.reset();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
		
		timer.reset();
		timer.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		
		if (timer.get() < 6.0) {
			//myRobot.drive(0.3, -0.0045);
			// drive.autoDrive(0.3625, -0.38);
			drive.autoStraightDrive(0.5, getGyroAngle()*0.05); // GYROCODE
		} else {
			drive.autoDrive(0.0, 0.0);
		}
		
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		//myRobot.arcadeDrive(stick);
		drive.arcadeDrive(stick);
		compressor.start();
		
		// PNEUMATICS CODE
		SmartDashboard.putBoolean("Is compressor running", compressor.enabled());
		SmartDashboard.putNumber("Compressor current draw:", compressor.getCompressorCurrent());
		SmartDashboard.putNumber("Total current draw:", pdp.getTotalCurrent());
		
		
		if (stick.getRawButton(2)) {
			piston.set(DoubleSolenoid.Value.kForward);
		} else if (stick.getRawButton(3)) {
			piston.set(DoubleSolenoid.Value.kReverse);
		} else {
			piston.set(DoubleSolenoid.Value.kOff);
		}

	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	public double getGyroAngle() {
		// return 0;
		return gyro.getAngle(); // GYRO CODE
	}
}
