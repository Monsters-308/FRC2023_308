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
import frc.robot.commands.chassis.DriveDistance;
import frc.robot.commands.chassis.BrakeDrive;
import frc.robot.commands.arm.ArmGotoAngle;
import frc.robot.commands.chassis.AutoBalance;

import com.kauailabs.navx.frc.AHRS;

//Constants
import frc.robot.Constants.ArmConstants;

//subsystems
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ArmSubsystem;

public class AutonOnePieceMiddleNoCommunity extends SequentialCommandGroup{

    public AutonOnePieceMiddleNoCommunity(ChassisSubsystem chassisSubsystem, ClawSubsystem clawSubsystem, ArmSubsystem armSubsystem, AHRS ahrs, double kInitialPitchOffset){
        addCommands(
            new SequentialCommandGroup(
                //Startup processes:
                //close claw
                new InstantCommand(clawSubsystem::closeClaw, clawSubsystem),
                new WaitCommand(0.2),

                //move arm to high level and put wrist down
                new ParallelCommandGroup(
                    new ArmGotoAngle(80, 0.9, armSubsystem),
                    new WaitCommand(0.18).andThen(new InstantCommand(clawSubsystem::wristDown, clawSubsystem))
                ),
                new WaitCommand(0.3),
                new ArmGotoAngle(ArmConstants.kTopPositionCube, ArmConstants.kTopSpeed, armSubsystem),

                //move forward
                new DriveDistance(15, 0.65, chassisSubsystem),
                new WaitCommand(0.25),

                //open claw 
                new InstantCommand(clawSubsystem::openClaw, clawSubsystem),
                new WaitCommand(0.2),
                
                //move backwards, put wrist up, and lower arm while moving backwards.
                new ParallelCommandGroup(
                    new DriveDistance(66, -1, chassisSubsystem),
                    new InstantCommand(clawSubsystem::wristUp, clawSubsystem),
                    new WaitCommand(2)
                    .andThen(new ArmGotoAngle(ArmConstants.kBottomPosition, ArmConstants.kBottomPosition, armSubsystem))
                ),
                new WaitCommand(0.25),

                //engage autobalance
                new RepeatCommand(
                    new AutoBalance(chassisSubsystem, ahrs, kInitialPitchOffset).withTimeout(0.6)
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
