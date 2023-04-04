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
//import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//Camera library
import edu.wpi.first.cameraserver.CameraServer;

//NAVX libraries
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;

//Constants
import frc.robot.Constants.IOConstants;
import frc.robot.Constants.ArmConstants;

//Commands
import frc.robot.commands.chassis.DefaultDrive;
import frc.robot.commands.chassis.AutoBalance;
import frc.robot.commands.chassis.BrakeDrive;
import frc.robot.commands.auton.AutonTest;
import frc.robot.commands.auton.AutonSide;
import frc.robot.commands.auton.AutonStartup;
import frc.robot.commands.auton.AutonMiddle;
import frc.robot.commands.auton.AutonOnePieceSide;
import frc.robot.commands.auton.AutonOnePieceMiddle;
import frc.robot.commands.auton.AutonOnePieceMiddle180;
import frc.robot.commands.auton.AutonOnePieceMiddleNoCommunity;
import frc.robot.commands.arm.ArmGotoAngle;
import frc.robot.commands.vision.AutoAlignTop;

//Subsystems
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.LEDSubsystem.LEDState;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.subsystems.NavSubsystem;

//Command libraries
import edu.wpi.first.wpilibj2.command.Command;
//import edu.wpi.first.wpilibj2.command.Commands;
//import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

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
  private final VisionSubsystem m_visionSubsystem = new VisionSubsystem();
  private final LEDSubsystem m_LEDSubsystem;
  
  
  //Controllers:
  XboxController m_driverController = new XboxController(IOConstants.kDriverPort);
  XboxController m_coDriverController = new XboxController(IOConstants.kCoDriverPort);
  
  //NavX:
  public AHRS ahrs;
  public double kInitialPitchOffset = 0;

  //SendableChooser<Command> m_autonChooser = new SendableChooser<Command>();


  /** The container for the robot. Contains subsystems, IO devices, and commands. */
  public RobotContainer() {
    
    //Enable the NavX
    try {
      ahrs = new AHRS(SPI.Port.kMXP);
      ahrs.enableLogging(true);
    } 
    catch (RuntimeException ex) {
      DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
    }

    //Get the initial pitch of the NavX (Since the robot will be slightly tilted)
    try {
      TimeUnit.SECONDS.sleep(2);
      kInitialPitchOffset = ahrs.getPitch();
    } 
    catch (InterruptedException e) {
      DriverStation.reportError("An error in getting the navX Pitch: " + e.getMessage(), true);
    }

    //Pass NavX and initial pitch into Nav subsystem
    m_navSubsystem = new NavSubsystem(ahrs, kInitialPitchOffset);
    
    //Pass NavX into LED subsystem so we can have the cool rainbow effect
    m_LEDSubsystem = new LEDSubsystem(ahrs);

    //Start-up of USB cameras for drivers
    CameraServer.startAutomaticCapture();

    //Configure the button bindings
    configureBindings();

    m_chassisSubsystem.setDefaultCommand(
      new DefaultDrive(m_chassisSubsystem,
      () -> -m_driverController.getLeftY(),
      () -> m_driverController.getRightX(),
      () -> m_driverController.getRightTriggerAxis() > IOConstants.kTriggerThreshold)
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


    //left trigger: brake mode
    new Trigger(() -> m_driverController.getLeftTriggerAxis() > IOConstants.kTriggerThreshold)
      .whileTrue(
        new BrakeDrive(m_chassisSubsystem,
        () -> -m_driverController.getLeftY(),
        () -> m_driverController.getRightX())
      );

    //Y button: auto aim (high pole)
    new JoystickButton(m_driverController, Button.kY.value)
      .whileTrue(
        new AutoAlignTop(m_visionSubsystem, m_chassisSubsystem, m_LEDSubsystem)
      );
    
    //A button: auto aim (mide pole)
    /*new JoystickButton(m_driverController, Button.kA.value)
      .whileTrue(
        new AutoAlign(m_visionSubsystem, m_chassisSubsystem, m_LEDSubsystem)
      );*/

    //Dpad up: reset encoders (for testing purposes)
    new POVButton(m_driverController, 0)
        .onTrue(
          //not setting requirements should prevent "Differential drive not updated enough"
          new InstantCommand(m_chassisSubsystem::resetEncoders)
        );
    
    //Dpad down: auto balance (for testing purposes)
    new POVButton(m_driverController, 180)
      .whileTrue(
        //I think if we create a command similar to an autonomous command, we could shove this entire composition into a single file.
        //However, autobalancing is the last thing we do in autonomous, so that probably wont be necessary.
        //Even though it's a repeating command, all autonomous commands should automatically be canceled once teleop is enabled.
        new RepeatCommand(
          new AutoBalance(m_chassisSubsystem, ahrs, kInitialPitchOffset).withTimeout(0.7)
          .andThen(
            new BrakeDrive(m_chassisSubsystem,
            () -> 0,
            () -> 0).withTimeout(0.5)
            )
          )
    );

    //Right trigger: Change LED mode for turbo mode (the actual code for turbo mode is handled within DefaultDrive itself).
    new Trigger(() -> m_driverController.getRightTriggerAxis() > IOConstants.kTriggerThreshold)
      .onTrue(
        new InstantCommand(() -> m_LEDSubsystem.changeLEDState(LEDState.TURBO)) 
      )
      .onFalse(
        new InstantCommand(() -> m_LEDSubsystem.changeLEDState(LEDState.RAINBOW)) 
    );

    //Left trigger: Change LED mode for Brake mode (the actual code for brake mode is handled within BrakeDrive).
    new Trigger(() -> m_driverController.getLeftTriggerAxis() > IOConstants.kTriggerThreshold)
      .onTrue(
        new InstantCommand(() -> m_LEDSubsystem.changeLEDState(LEDState.TEAL)) 
      )
      .onFalse(
        new InstantCommand(() -> m_LEDSubsystem.changeLEDState(LEDState.RAINBOW)) 
    );



    /**
     * CO-DRIVER BUTTONS 
     */


    //Dpad left: Set arm to bottom
    new POVButton(m_coDriverController, 270)
      .onTrue(
        new ArmGotoAngle(ArmConstants.kBottomPosition, ArmConstants.kBottomSpeed, m_armSubsystem)
      );
    
    
    //Dpad up: Set arm to middle goal
    new POVButton(m_coDriverController, 0)
      .onTrue(
        new ArmGotoAngle(ArmConstants.kMiddlePosition, ArmConstants.kMiddleSpeed, m_armSubsystem)
      );

    
    //Dpad right: Set arm to top goal
    new POVButton(m_coDriverController, 90)
      .onTrue(
        new ArmGotoAngle(ArmConstants.kTopPositionCone, ArmConstants.kTopSpeed, m_armSubsystem)
      );

    //Right bumper: Raise the arm up manually
    new JoystickButton(m_coDriverController, Button.kRightBumper.value)
      .onTrue(
       new InstantCommand(m_armSubsystem::up, m_armSubsystem)
      )
      .onFalse(
       new InstantCommand(m_armSubsystem::stop, m_armSubsystem)
      );

    //Left bumper: Lower the arm down manually
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
    //return new AutonOnePieceMiddleNoCommunity(m_chassisSubsystem, m_clawSubsystem, m_armSubsystem, ahrs, kInitialPitchOffset);
    return new AutonOnePieceSide(m_chassisSubsystem, m_clawSubsystem, m_armSubsystem);
  }
}
