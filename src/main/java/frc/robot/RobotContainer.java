// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//Controller stuff
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
// button mapping
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

//Shuffleboard stuff
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.concurrent.TimeUnit;

//camera imports
import edu.wpi.first.cameraserver.CameraServer;

//NAVX
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;

//constants
import frc.robot.Constants.IOConstants;

//Commands
import frc.robot.commands.chassis.DefaultDrive;
import frc.robot.commands.chassis.MainAutoBalance;
import frc.robot.commands.auton.AutonTest;

//Subsystems go here
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.VisionSubsystem;

//command stuff
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;




/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  
  //Subsystems:
  private final ChassisSubsystem m_chassisSubsystem = new ChassisSubsystem();
  private final ArmSubsystem m_armSubsystem = new ArmSubsystem();
  private final ClawSubsystem m_clawSubsystem = new ClawSubsystem();
  //private final VisionSubsystem m_visionSubsystem = new VisionSubsystem();
  
  //Controllers:
  XboxController m_driverController = new XboxController(IOConstants.kDriverPort);
  XboxController m_coDriverController = new XboxController(IOConstants.kCoDriverPort);
  
  public AHRS ahrs; //Ask Marcus about whether or not this should be public

  public double kInitialPitchOffset = 0;



  /** The container for the robot. Contains subsystems, IO devices, and commands. */
  public RobotContainer() {
    
    //Enable the NavX
    try {
      ahrs = new AHRS(SPI.Port.kMXP);
      // ahrs = new AHRS(SerialPort.Port.kUSB1);
      ahrs.enableLogging(true);
    } catch (RuntimeException ex) {
      DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
    }

    //Get the initial pitch of the NavX (Since the board isn't mounted horizonally)
    try {
      TimeUnit.SECONDS.sleep(2);
      kInitialPitchOffset = ahrs.getYaw();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    // //set up camera stuff
    // CameraServer.startAutomaticCapture();

    // Configure the button bindings
    configureBindings();

    m_chassisSubsystem.setDefaultCommand(
      new DefaultDrive(m_chassisSubsystem,
      () -> -m_driverController.getLeftY(),
      () -> m_driverController.getRightX())
    );
    
      

  }


  /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
     * it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
  private void configureBindings() {


    //MAIN DRIVER BUTTONS:

    //Right trigger: autobalance
    new JoystickButton(m_driverController,Button.kA.value)
        .onTrue(
          new MainAutoBalance(m_chassisSubsystem, ahrs, kInitialPitchOffset)
        )
        .onFalse(
          new DefaultDrive(m_chassisSubsystem, //this isn't the most elagant solution but whatever
          () -> -m_driverController.getLeftY(),
          () -> m_driverController.getRightX())
        );
    

    


    //CO DRIVER BUTTONS:


    //Dpad left: bottom
    new POVButton(m_driverController, 270)
      .onTrue(
        new InstantCommand(m_armSubsystem::bottomLevel, m_armSubsystem)
      );


    
    //Dpad up: middle
    new POVButton(m_driverController, 0)
    .onTrue(
      new InstantCommand(m_armSubsystem::middleLevel, m_armSubsystem)
    );



    //Dpad right: top
    new POVButton(m_driverController, 90)
    .onTrue(
      new InstantCommand(m_armSubsystem::topLevel, m_armSubsystem)
    );



    //Dpad down: loading
    new POVButton(m_driverController, 180)
    .onTrue(
      new InstantCommand(m_armSubsystem::loadingLevel, m_armSubsystem)
    );



    //Right trigger: manual up
    new Trigger(() -> m_driverController.getRightTriggerAxis() > IOConstants.kTriggerThreshold)
      .onTrue(
       new InstantCommand(m_armSubsystem::up, m_armSubsystem)
      );
      // .onFalse(
      //  new InstantCommand(m_armSubsystem::stop, m_armSubsystem)
      // );



    //Left trigger: manual down
    new Trigger(() -> m_driverController.getLeftTriggerAxis() > IOConstants.kTriggerThreshold)
    .onTrue(
      new InstantCommand(m_armSubsystem::down, m_armSubsystem)
    );
    // .onFalse(
    //   new InstantCommand(m_armSubsystem::stop, m_armSubsystem)
    // );



    //A button: toggle claw (B if it's one controller)
    new JoystickButton(m_driverController,Button.kB.value)
      .onTrue(
        new InstantCommand(m_clawSubsystem::toggleSolenoid, m_clawSubsystem)
      );

    


  }



  /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
  public Command getAutonomousCommand() {
    return new AutonTest(m_chassisSubsystem);
  }
}
