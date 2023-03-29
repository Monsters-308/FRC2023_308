/**
 * This auton will be used for the sides of the field. It will place a cube on the top level and then back out of the community zone.
 * Before running this auton, we need to test the arm code once they get the arm fixed.
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
import com.kauailabs.navx.frc.AHRS;

//Constants
import frc.robot.Constants.ArmConstants;

//subsystems
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ArmSubsystem;

public class AutonOnePieceMiddle extends SequentialCommandGroup{

    public AutonOnePieceMiddle(ChassisSubsystem chassisSubsystem, ClawSubsystem clawSubsystem, ArmSubsystem armSubsystem, AHRS ahrs){
        addCommands(
            new SequentialCommandGroup(
                //Startup processes:
                //close claw
                //wrist down
                new InstantCommand(clawSubsystem::closeClaw, clawSubsystem),
                new WaitCommand(0.2),
                new InstantCommand(clawSubsystem::wristDown, clawSubsystem),

                //move arm to high level
                new ArmGotoAngle(ArmConstants.kTopPositionCube, ArmConstants.kTopSpeed, armSubsystem),
                //move forward
                new DriveDistanceRotations(15, 0.7, chassisSubsystem),
                new WaitCommand(0.25),
                //open claw 
                new InstantCommand(clawSubsystem::openClaw, clawSubsystem),
                new WaitCommand(0.2),
                
                new ParallelCommandGroup(
                    new DriveDistanceRotations(120, -0.9, chassisSubsystem),
                    new InstantCommand(clawSubsystem::wristUp, clawSubsystem),
                    new WaitCommand(2)
                    .andThen(new ArmGotoAngle(ArmConstants.kBottomPosition, ArmConstants.kBottomPosition, armSubsystem))
                ),
                new DriveDistanceRotations(49, 0.7, chassisSubsystem),
                new WaitCommand(0.25),
                //engage autobalance
                new RepeatCommand(
                    new AutoBalance(chassisSubsystem, ahrs, 0).withTimeout(0.7)
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
