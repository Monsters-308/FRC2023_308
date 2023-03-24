/**
 * This auton will be used for the sides of the field. It will place a cube on the top level and then back out of the community zone.
 * Before running this auton, we need to test the arm code once they get the arm fixed.
 * Line robot up with the edge of the charge pad and in front of the cube column of the loading zone.
 */
package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


//Commands
import frc.robot.commands.chassis.DriveDistanceInches;
import frc.robot.commands.chassis.DriveDistanceRotations;
import frc.robot.commands.arm.ArmGotoAngle;

//Constants
import frc.robot.Constants.ArmConstants;

//subsystems
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ArmSubsystem;

public class AutonOnePieceSide extends SequentialCommandGroup{

    public AutonOnePieceSide(ChassisSubsystem chassisSubsystem, ClawSubsystem clawSubsystem, ArmSubsystem armSubsystem){
        addCommands(
            new SequentialCommandGroup(
                //Startup processes:
                //close claw
                //wrist down
                new InstantCommand(clawSubsystem::closeClaw, clawSubsystem),
                new WaitCommand(0.5),
                new InstantCommand(clawSubsystem::wristDown, clawSubsystem),

                //move arm to high level
                new ArmGotoAngle(ArmConstants.kTopPosition, ArmConstants.kTopSpeed, armSubsystem),
                //move forward
                new DriveDistanceRotations(50, 0.5, chassisSubsystem),
                new WaitCommand(0.25),
                //open claw 
                new InstantCommand(clawSubsystem::openClaw, clawSubsystem),
                new WaitCommand(0.5),
                //backup away from loading zone
                new ParallelCommandGroup(
                    new DriveDistanceRotations(50, -0.6, chassisSubsystem),
                    new InstantCommand(clawSubsystem::wristUp, clawSubsystem)
                ),
                new WaitCommand(0.25),

                //lower arm
                new ArmGotoAngle(ArmConstants.kBottomPosition, ArmConstants.kBottomSpeed, armSubsystem),
                //backup out of community zone
                new DriveDistanceRotations(50, -0.6, chassisSubsystem)
                

            )
        );
    }
}
