/**
 * This is the old side auton from Milford.
 * Semi-consistently places low and then backs up out of the community.
 * Probably won't work anymore since our encoder distances have probably changed with the addition of 2 more motors.
 * Robot must be started in front of the loading zone.
 */
package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
//import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

//Commands
//import frc.robot.commands.chassis.DriveDistanceInches;
import frc.robot.commands.chassis.DriveDistance;
//import frc.robot.commands.chassis.AutoTurn;

//subsystems
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ArmSubsystem;

public class AutonSide extends SequentialCommandGroup{

    public AutonSide(ChassisSubsystem chassisSubsystem, ClawSubsystem clawSubsystem, ArmSubsystem armSubsystem){
        addCommands(

            new SequentialCommandGroup(

                //Puts wrist down, closes claw, moves forward, open claw, move backwards out of community.
                new InstantCommand(clawSubsystem::wristDown, clawSubsystem),
                new WaitCommand(1),
                new InstantCommand(clawSubsystem::closeClaw, clawSubsystem),
                new WaitCommand(1),
                
                new DriveDistance(15, 0.4, chassisSubsystem),
                new WaitCommand(1),
                new InstantCommand(clawSubsystem::openClaw, clawSubsystem),
                new WaitCommand(0.5),

                new DriveDistance(117, -0.6, chassisSubsystem)
            )
        );
    }
    
}
