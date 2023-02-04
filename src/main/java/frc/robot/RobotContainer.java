// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

// Shuffleboard stuff
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// Command stuff
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands; // probably not usefull
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

// Commands
import frc.robot.commands.drive.ArcadeDrive;
import frc.robot.commands.drive.DefaultDrive;
import frc.robot.commands.drive.DriveDistance;
import frc.robot.commands.drive.DriveTurn2;
import frc.robot.commands.drive.StopDrive;

// controller stuff
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import static frc.robot.Constants.IOConstants;

// subsystems
import frc.robot.subsystems.DriveSubsystem;


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

  XboxController m_driverController = new XboxController(IOConstants.kControllerDrivePort);

  private final DriveSubsystem m_driveSubsystem = new DriveSubsystem(m_driverController);
  
  SendableChooser<Command> m_driveChooser = new SendableChooser<Command>();
  
  /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
  public RobotContainer() {
    configureBindings();


    m_driveChooser.addOption("Tank Drive",
              new DefaultDrive(m_driveSubsystem, m_driverController::getLeftY, m_driverController::getRightY,
                      m_driverController::getRightBumper));
    m_driveChooser.setDefaultOption("Arcade Drive",
              new ArcadeDrive(m_driveSubsystem, m_driverController::getLeftY, m_driverController::getRightX,
                      m_driverController::getRightBumper)); 
    
    SmartDashboard.updateValues();
              
              


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
    // All of this is deprecated but whatever
  }



  /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
