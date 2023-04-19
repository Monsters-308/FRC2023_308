/**
 * This auton will be used for the sides of the field. It will place a cube on the top level, back out of the community zone, and then rotate towards the game pieces.
 * Line robot up with the edge of the charge pad and in front of the cube column of the loading zone.
 * The robot should be about 27 inches away from the goal.
 */
package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
//import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.chassis.AutoTurn;
//Commands
//import frc.robot.commands.chassis.DriveDistanceInches;
import frc.robot.commands.chassis.DriveDistance;
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
                new InstantCommand(clawSubsystem::closeClaw, clawSubsystem),
                new WaitCommand(0.2),

                //move arm to high level and put wrist down
                new ParallelCommandGroup(
                    new ArmGotoAngle(ArmConstants.kTopPositionCube, ArmConstants.kTopSpeed, armSubsystem),
                    new WaitCommand(0.5).andThen(new InstantCommand(clawSubsystem::wristDown, clawSubsystem))
                ),

                //move forward
                new DriveDistance(15, 0.7, chassisSubsystem),
                new WaitCommand(0.25),

                //open claw 
                new InstantCommand(clawSubsystem::openClaw, clawSubsystem),
                new WaitCommand(0.2),

                //backup so the arm won't hit the goal
                new DriveDistance(15, -0.7, chassisSubsystem),

                //backup out of community zone while lowering arm and putting the claw up
                new ParallelCommandGroup(
                    new DriveDistance(85, -0.8, chassisSubsystem),
                    new InstantCommand(clawSubsystem::wristUp, clawSubsystem),
                    new ArmGotoAngle(ArmConstants.kBottomPosition, ArmConstants.kBottomSpeed, armSubsystem)
                ),
                new WaitCommand(0.25),
                
                //rotate towards game pieces
                new AutoTurn(chassisSubsystem, 64, 0.8)
            )
        );
    }
}
