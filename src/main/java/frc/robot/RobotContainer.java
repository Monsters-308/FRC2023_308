// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//Controller Libraries
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
//Button Libraries
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
//Command Libraries
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
//Camera Libraries
import edu.wpi.first.cameraserver.CameraServer;
//NAVX Libraries
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
//Constants
import frc.robot.Constants.IOConstants;
//Commands
import frc.robot.commands.chassis.DefaultDrive;
import frc.robot.commands.chassis.MainAutoBalance;
//Auto-mode Programs
import frc.robot.commands.auton.AutonMiddle;
import frc.robot.commands.auton.AutonSide;
//Subsystems
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.NavSubsystem;

import java.util.concurrent.TimeUnit;

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
  private final NavSubsystem m_navSubsystem;
  private final LEDSubsystem m_LEDSubsystem = new LEDSubsystem();
  
  //Controllers:
  XboxController m_driverController = new XboxController(IOConstants.kDriverPort);
  XboxController m_coDriverController = new XboxController(IOConstants.kCoDriverPort);
  
  //Navx component
  private AHRS ahrs;
  private double kInitialYawOffset = 0;

  /** The container for the robot. Contains subsystems, IO devices, and commands. */
  public RobotContainer() {
    
    //Enable the NavX
    try {
      ahrs = new AHRS(SPI.Port.kMXP);
      ahrs.enableLogging(true);
    } catch (RuntimeException ex) {
      DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
    }

    //Get the initial pitch of the NavX (Since the board isn't mounted horizonally)
    try {
      TimeUnit.SECONDS.sleep(2);
      //RoboRio is flipped 90 degrees so we now need yaw as to pitch, though I think this wrong - Marcus
      kInitialYawOffset = ahrs.getYaw();
    } catch (InterruptedException e) {
      DriverStation.reportError("An error in getting the navX Yaw: " + e.getMessage(), true);
    }

    //Create NavX subsystem with 
    m_navSubsystem = new NavSubsystem(ahrs, kInitialYawOffset);
    
    //Start-up of USB cameras for drivers
    CameraServer.startAutomaticCapture();

    //Configure the button bindings
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
    /**
     * DRIVER BUTTONS
     */

    //Right bumper: autobalance
    new JoystickButton(m_driverController, Button.kRightBumper.value)
        .onTrue(
          new MainAutoBalance(m_chassisSubsystem, ahrs, kInitialYawOffset)
        )
        .onFalse(
          m_chassisSubsystem.getDefaultCommand()
        );
    //Left bumper: autosnap to nearest scoreing element?
    /* new JoystickButton(m_driverController, Button.kLeftBumper.value)
        .onTrue(
          new AutoSnap() //STUB
        )
        .onFalse(
          new DefaultDrive(m_chassisSubsystem, //this isn't the most elagant solution but whatever
          () -> -m_driverController.getLeftY(),
          () -> m_driverController.getRightX())
        );
    */

    /**
     * CO-DRIVER BUTTONS 
     */

    //Dpad left: Set arm to bottom (Floor Tier) score area
    new POVButton(m_coDriverController, 270)
      .onTrue(
        new InstantCommand(m_armSubsystem::bottomLevel, m_armSubsystem)
      );

    //Dpad up: Set arm to middle (1st Tier) score area
    new POVButton(m_coDriverController, 0)
    .onTrue(
      new InstantCommand(m_armSubsystem::middleLevel, m_armSubsystem)
    );

    //Dpad right: Set arm to top (2nd Tier) score area
    new POVButton(m_coDriverController, 90)
    .onTrue(
      new InstantCommand(m_armSubsystem::topLevel, m_armSubsystem)
    );

    //Dpad down: loading (Need still? - Marcus)
    /*new POVButton(m_driverController, 180)
    .onTrue(
      new InstantCommand(m_armSubsystem::loadingLevel, m_armSubsystem)
    );*/

    //Right bumper: Raise the arm up manually
    new JoystickButton(m_coDriverController, Button.kRightBumper.value)
      .onTrue(
       new InstantCommand(m_armSubsystem::up, m_armSubsystem)
      )
      .onFalse(
       new InstantCommand(m_armSubsystem::stop, m_armSubsystem)
      );

    //Left bumper: Lower the arm up manually
    new JoystickButton(m_coDriverController, Button.kLeftBumper.value)
    .onTrue(
      new InstantCommand(m_armSubsystem::down, m_armSubsystem)
    )
    .onFalse(
      new InstantCommand(m_armSubsystem::stop, m_armSubsystem)
    );

    //A button: Toggle claw to close and open (Open by default)
    new JoystickButton(m_coDriverController, Button.kA.value)
      .onTrue(
        new InstantCommand(m_clawSubsystem::toggleClaw, m_clawSubsystem)
      );

    //X button: Toggle wrist in up or down position (Up by default)
    new JoystickButton(m_coDriverController,Button.kX.value)
    .onTrue(
      new InstantCommand(m_clawSubsystem::toggleWrist, m_clawSubsystem)
    );
  }

  /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
  public Command getAutonomousCommand() {
    //Flip wrist down, toggle claw, drive backwards out of community
    return new AutonSide(m_chassisSubsystem, m_clawSubsystem, m_armSubsystem);

    /**
     * NOTE: Should use  a shuffleboard toggle to pick between our diffrent autonomous modes - Marcus
     */
  }
}
