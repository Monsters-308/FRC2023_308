// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//Controller libraries
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;

//Button libraries
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

//Shuffleboard libraries
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//Camera library
import edu.wpi.first.cameraserver.CameraServer;

//NAVX libraries
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;

//Constants
import frc.robot.Constants.IOConstants;

//Commands
import frc.robot.commands.chassis.DefaultDrive;
import frc.robot.commands.chassis.AutoBalance;
import frc.robot.commands.chassis.AutoBalanceStutter;
import frc.robot.commands.auton.AutonTest;
import frc.robot.commands.auton.AutonSide;
import frc.robot.commands.auton.AutonMiddle;

//Subsystems
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.LEDSubsystem.LEDState;
//import frc.robot.subsystems.VisionSubsystem;
import frc.robot.subsystems.NavSubsystem;

//Command libraries
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

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
  //private final VisionSubsystem m_visionSubsystem = new VisionSubsystem();
  //private final LEDSubsystem m_LEDSubsystem = new LEDSubsystem();
  
  
  //Controllers:
  XboxController m_driverController = new XboxController(IOConstants.kDriverPort);
  XboxController m_coDriverController = new XboxController(IOConstants.kCoDriverPort);
  
  //NavX:
  public AHRS ahrs;
  public double kInitialPitchOffset = 0;

  //SendableChooser<Command> m_autonChooser = new SendableChooser<>();


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
      kInitialPitchOffset = ahrs.getYaw();
    } catch (InterruptedException e) {
      DriverStation.reportError("An error in getting the navX Yaw: " + e.getMessage(), true);
    }

    //Pass NavX and initial pitch into Nav subsystem
    m_navSubsystem = new NavSubsystem(ahrs, kInitialPitchOffset);
    
    //Start-up of USB cameras for drivers
    CameraServer.startAutomaticCapture();

    //Configure the button bindings
    configureBindings();

    m_chassisSubsystem.setDefaultCommand(
      new DefaultDrive(m_chassisSubsystem,
      () -> -m_driverController.getLeftY(),
      () -> m_driverController.getRightX())
    );
    
    //Make it so we can select the auton mode from shuffleboard
    /*m_autonChooser.addOption("AutonMiddle",new AutonMiddle(m_chassisSubsystem, m_clawSubsystem, m_armSubsystem));
    m_autonChooser.addOption("AutonSide",new AutonSide(m_chassisSubsystem, m_clawSubsystem, m_armSubsystem));
    m_autonChooser.addOption("AutonTest",new AutonTest(m_chassisSubsystem, m_clawSubsystem, m_armSubsystem));
     */

    //Shuffleboard.getTab("Autonomous").add(m_autonChooser).withSize(2,1);
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


    //Use this for when we get LED code working:
    //new InstantCommand(() -> m_LEDSubsystem.changeLEDState(LEDState.BLINK)) 

    /** 
    * MAIN DRIVER BUTTONS:
    */

    //Right bumper: Balance mode
    //For debugging purposes, I will have the auto balance tethered to this
    /*new JoystickButton(m_driverController, Button.kRightBumper.value)
        .onTrue(
          new InstantCommand(m_chassisSubsystem::setBrakeMode, m_chassisSubsystem)
        )
        .onFalse(
          new InstantCommand(m_chassisSubsystem::setCoastMode, m_chassisSubsystem)
        );*/
    new JoystickButton(m_driverController, Button.kRightBumper.value)
      .onTrue(
        new AutoBalanceStutter(m_chassisSubsystem, ahrs, kInitialPitchOffset)
      )
      .onFalse(
        new DefaultDrive(m_chassisSubsystem,
        () -> -m_driverController.getLeftY(),
        () -> m_driverController.getRightX())
      );
    
    //TODO: add auto aim with limelight
    //Left bumper: auto aim 
    
    //Y button: reset encoders (for testing purposes)
    new JoystickButton(m_driverController, Button.kY.value)
        .onTrue(
          //not setting requirements should prevent "Differential drive not updated enough"
          new InstantCommand(m_chassisSubsystem::resetEncoders) 
          //new InstantCommand(() -> m_LEDSubsystem.changeLEDState(LEDState.BLINK)) 
        );

    /**
     * CO-DRIVER BUTTONS 
     */

    //Dpad left: Set arm to bottom
    /*new POVButton(m_coDriverController, 270)
      .onTrue(
        new InstantCommand(m_armSubsystem::bottomLevel, m_armSubsystem)
      );
    */

    /*
    //Dpad up: Set arm to middle goal
    new POVButton(m_coDriverController, 0)
    .onTrue(
      new InstantCommand(m_armSubsystem::middleLevel, m_armSubsystem)
    );*/

    /*
    //Dpad right: Set arm to top goal
    new POVButton(m_coDriverController, 90)
    .onTrue(
      new InstantCommand(m_armSubsystem::topLevel, m_armSubsystem)
    );*/
    
    /* 
    //Dpad down: We might use this for a separate loading level or something
    new POVButton(m_driverController, 180)
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

    //A button: Toggle claw 
    new JoystickButton(m_coDriverController, Button.kA.value)
      .onTrue(
        new InstantCommand(m_clawSubsystem::toggleClaw, m_clawSubsystem)
      );

    //X button: Toggle wrist (move claw up or down) 
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
    //TODO: add a shuffleboard selector that doesn't break the robot
    //return m_autonChooser.getSelected();
    return new AutonSide(m_chassisSubsystem, m_clawSubsystem, m_armSubsystem);
  }
}
