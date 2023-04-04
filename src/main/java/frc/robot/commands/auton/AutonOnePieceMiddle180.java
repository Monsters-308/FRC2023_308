/**
 * This auton will be used for the middle of the field. It will place a cube on the top level, back out of the community zone, and then move forward and balance.
 * Line robot up with the edge of the charge pad and in front of the cube column of the loading zone.
 * The robot should be about 27 inches away from the goal.
 */
package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
//import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
//import frc.robot.commands.chassis.AutoTurnRotations;

//Commands
//import frc.robot.commands.chassis.DriveDistanceInches;
import frc.robot.commands.chassis.DriveDistanceRotations;
import frc.robot.commands.chassis.BrakeDrive;
import frc.robot.commands.arm.ArmGotoAngle;
import frc.robot.commands.chassis.AutoBalance;
import frc.robot.commands.chassis.AutoTurnRotations;

import com.kauailabs.navx.frc.AHRS;

//Constants
import frc.robot.Constants.ArmConstants;

//subsystems
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ArmSubsystem;

public class AutonOnePieceMiddle180 extends SequentialCommandGroup{

    public AutonOnePieceMiddle180(ChassisSubsystem chassisSubsystem, ClawSubsystem clawSubsystem, ArmSubsystem armSubsystem, AHRS ahrs, double kInitialPitchOffset){
        addCommands(
            new SequentialCommandGroup(
                //Startup processes:
                //close claw
                new WaitCommand(0.4),
                new InstantCommand(clawSubsystem::closeClaw, clawSubsystem),
               

                //move arm to high level and put wrist down
                new ParallelCommandGroup(
                    new ArmGotoAngle(ArmConstants.kTopPositionCube, ArmConstants.kTopSpeed, armSubsystem),
                    new WaitCommand(0.1).andThen(new InstantCommand(clawSubsystem::wristDown, clawSubsystem))
                ),

                //move forward
                new DriveDistanceRotations(15, 0.7, chassisSubsystem),
                new WaitCommand(0.25),

                //open claw 
                new InstantCommand(clawSubsystem::openClaw, clawSubsystem),
                new WaitCommand(0.2),
                
                //move backwards, put wrist up, and lower arm while moving backwards.
                new ParallelCommandGroup(
                    new DriveDistanceRotations(123, -1, chassisSubsystem),
                    new InstantCommand(clawSubsystem::wristUp, clawSubsystem),
                    new WaitCommand(2)
                    .andThen(new ArmGotoAngle(ArmConstants.kBottomPosition, ArmConstants.kBottomPosition, armSubsystem))
                ),
                new WaitCommand(0.25),

                //turn 180 degrees
                new AutoTurnRotations(chassisSubsystem, 63, 0.9),
                new WaitCommand(0.25),

                //move forwards
                new DriveDistanceRotations(47, -1, chassisSubsystem),
                new WaitCommand(0.25),

                //engage autobalance
                new RepeatCommand(
                    new AutoBalance(chassisSubsystem, ahrs, kInitialPitchOffset).withTimeout(0.7)
                    .andThen(
                    new BrakeDrive(chassisSubsystem,
                        () -> 0,
                        () -> 0).withTimeout(0.5)
                  )
                )
            )
        );
    }
}
